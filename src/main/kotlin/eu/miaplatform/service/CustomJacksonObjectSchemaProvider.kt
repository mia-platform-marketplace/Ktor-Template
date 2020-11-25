package eu.miaplatform.service

import com.fasterxml.jackson.annotation.JsonProperty
import com.papsign.ktor.openapigen.*
import com.papsign.ktor.openapigen.model.schema.SchemaModel
import com.papsign.ktor.openapigen.modules.DefaultOpenAPIModule
import com.papsign.ktor.openapigen.modules.ModuleProvider
import com.papsign.ktor.openapigen.modules.ofType
import com.papsign.ktor.openapigen.schema.builder.FinalSchemaBuilder
import com.papsign.ktor.openapigen.schema.builder.SchemaBuilder
import com.papsign.ktor.openapigen.schema.builder.provider.SchemaBuilderProviderModule
import com.papsign.ktor.openapigen.schema.namer.DefaultSchemaNamer
import com.papsign.ktor.openapigen.schema.namer.SchemaNamer
import org.slf4j.LoggerFactory
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.full.withNullability
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.typeOf

object CustomJacksonObjectSchemaProvider : SchemaBuilderProviderModule, OpenAPIGenModuleExtension, DefaultOpenAPIModule {
    private val log = LoggerFactory.getLogger(CustomJacksonObjectSchemaProvider::class.java)

    override fun provide(apiGen: OpenAPIGen, provider: ModuleProvider<*>): List<SchemaBuilder> {
        val namer = provider.ofType<SchemaNamer>().let {
            val last = it.lastOrNull() ?: DefaultSchemaNamer.also { log.debug("No ${SchemaNamer::class} provided, using ${it::class}") }
            if (it.size > 1) log.warn("Multiple ${SchemaNamer::class} provided, choosing last: ${last::class}")
            last
        }
        return listOf(
            Builder(
                apiGen,
                namer
            )
        )
    }

    private class Builder(private val apiGen: OpenAPIGen, private val namer: SchemaNamer) : SchemaBuilder {
        @ExperimentalStdlibApi
        internal inline fun <reified T> getKType() = typeOf<T>()
        @ExperimentalStdlibApi
        override val superType: KType = getKType<Any?>()

        private val refs = HashMap<KType, SchemaModel.SchemaModelRef<*>>()

        override fun build(type: KType, builder: FinalSchemaBuilder, finalize: (SchemaModel<*>)-> SchemaModel<*>): SchemaModel<*> {
            checkType(type)
            val nonNullType = type.withNullability(false)
            type.annotations.find { it.annotationClass == KType::class }
            return refs[nonNullType] ?: {
                val erasure = nonNullType.jvmErasure
                val name = namer[nonNullType]
                val ref = SchemaModel.SchemaModelRef<Any?>("#/components/schemas/$name")
                refs[nonNullType] = ref // needed to prevent infinite recursion
                val new = if (erasure.isSealed) {
                    SchemaModel.OneSchemaModelOf(erasure.sealedSubclasses.map { builder.build(it.starProjectedType) })
                } else {
                    val props = type.memberProperties.filter { it.source.visibility == KVisibility.PUBLIC }
                    SchemaModel.SchemaModelObj<Any?>(
                        props.associate {
                            val annotation = it.source.getter.annotations.find { type -> type is JsonProperty } as? JsonProperty
                            val propertyName = annotation?.value ?: it.name
                            Pair(propertyName, builder.build(it.type, it.source.annotations))
                        },
                        props.filter {
                            !it.type.isMarkedNullable
                        }.map {
                            val annotation = it.source.getter.annotations.find { type -> type is JsonProperty } as? JsonProperty
                            annotation?.value ?: it.name
                        }
                    )
                }
                val final = finalize(new)
                val existing = apiGen.api.components.schemas[name]
                if (existing != null && existing != final) log.error("Schema with name $name already exists, and is not the same as the new one, replacing...")
                apiGen.api.components.schemas[name] = final
                ref
            }()
        }
    }
}
package eu.miaplatform.service.model

import com.papsign.ktor.openapigen.APITag

object ServiceTag: APITag {
    override val name: String
        get() = "Name"
    override val description: String
        get() = "Description"

}
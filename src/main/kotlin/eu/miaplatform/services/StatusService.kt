package eu.miaplatform.services

import java.util.*

class StatusService {
    private val versionProperties = Properties()

    init {
        try {
            versionProperties.load(this.javaClass.getResourceAsStream("/version.properties"))
        } catch (e: Exception) {}
    }

    fun getVersion() : String = versionProperties.getProperty("version") ?: "no version"

}
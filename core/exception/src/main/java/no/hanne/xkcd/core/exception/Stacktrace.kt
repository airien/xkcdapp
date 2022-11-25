package no.hanne.xkcd.core.exception

import kotlinx.serialization.Serializable

@Serializable
data class Stacktrace(
    val file: String?,
    val line: Int,
    val method: String?
) : java.io.Serializable

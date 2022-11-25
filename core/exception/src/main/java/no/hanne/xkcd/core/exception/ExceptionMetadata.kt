package no.hanne.xkcd.core.exception

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionMetadata(val key: String, val value: String) : java.io.Serializable

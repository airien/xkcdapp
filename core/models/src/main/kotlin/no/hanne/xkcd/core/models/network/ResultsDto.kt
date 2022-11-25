package no.hanne.xkcd.core.models.network

import kotlinx.serialization.Serializable

@Serializable
data class ResultsDto<T>(
    val results: T
) : java.io.Serializable {
    fun toData(): T {
        return results
    }
}

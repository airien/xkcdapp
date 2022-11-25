package no.hanne.xkcd.core.repository

import android.content.res.Resources
import arrow.core.Either
import no.hanne.xkcd.core.exception.DataSourceException
import no.hanne.xkcd.core.exception.DataSourceResult
import no.hanne.xkcd.core.models.network.Result
import no.hanne.xkcd.core.network.toResult

class NetworkRequestHandler(
    private val handleTokenRefresh: suspend () -> Unit,
    private val resources: Resources,
) {

    suspend fun <T> run(
        onComplete: suspend () -> DataSourceResult<T>
    ) = Either.catch {
        val result = when (val res = onComplete().toResult()) {
            is Result.Success -> res.value
            is Result.Failure -> throw res.error
            else -> {
                throw UnknownError()
            }
        }
        result
    }.mapLeft {
        val error = when (it) {
            is DataSourceException -> it
            else -> DataSourceException.OtherError(it)
        }
        RepositoryUtils.handleDataSourceException(error, resources)
    }.toResult()

    /**
     * Function that will run request with Unit return type
     */
    suspend fun runVoid(
        onComplete: suspend () -> Unit
    ) = run {
        Either.catch {
            onComplete()
        }.mapLeft {
            when (it) {
                is DataSourceException -> it
                else -> DataSourceException.OtherError(it)
            }
        }
    }
}

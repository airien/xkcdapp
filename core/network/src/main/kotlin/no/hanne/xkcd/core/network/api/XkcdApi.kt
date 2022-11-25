package no.hanne.xkcd.core.network.api

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import no.hanne.xkcd.core.exception.DataSourceException
import no.hanne.xkcd.core.exception.DataSourceResult
import no.hanne.xkcd.core.models.network.NetworkingConstants
import no.hanne.xkcd.core.models.xkcd.Comic

class XkcdApi @Inject constructor(
    private val httpClient: HttpClient,
    private val networkingConstants: NetworkingConstants
) {
    suspend fun getLatestComic(): DataSourceResult<Comic> = Either.catch {
        httpClient.get<Comic>("${networkingConstants.xkcdUrl}info.0.json")
    }
        .mapLeft {
            when (it) {
                is DataSourceException -> it
                else -> DataSourceException.ApiError(it)
            }
        }

    suspend fun getComic(num: Int): DataSourceResult<Comic> = Either.catch {
        httpClient.get<Comic>("${networkingConstants.xkcdUrl}$num/info.0.json")
    }
        .mapLeft {
            when (it) {
                is DataSourceException -> it
                else -> DataSourceException.ApiError(it)
            }
        }
}

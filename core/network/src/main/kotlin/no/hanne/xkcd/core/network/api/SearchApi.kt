package no.hanne.xkcd.core.network.api

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import no.hanne.xkcd.core.exception.DataSourceException
import no.hanne.xkcd.core.exception.DataSourceResult
import no.hanne.xkcd.core.models.network.NetworkingConstants
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.models.xkcd.Search
import no.hanne.xkcd.core.models.xkcd.SearchComicRequest
import no.hanne.xkcd.core.models.xkcd.SearchResult

class SearchApi @Inject constructor(
    private val httpClient: HttpClient,
    private val networkingConstants: NetworkingConstants
) {
    suspend fun search(term: String): DataSourceResult<List<Comic>> = Either.catch {
        httpClient.post<SearchResult>(
            networkingConstants.typesenceUrl +
                "&x-typesense-api-key=${networkingConstants.typesenceUrlAppKey}"
        ) {
            contentType(ContentType.Application.Json)
            body = SearchComicRequest(
                listOf(
                    Search(
                        query = term,
                        collection = "xkcd",
                        queryBy = "title,altTitle,transcript,topics",
                        queryByWeights = "127,80,80,1",
                        dropTokensThreshold = 2,
                        typoTokensThreshold = 0,
                        numTypos = 1,
                        sortBy = "",
                        highlightFullFields = "title,altTitle,transcript,topics",
                        facetBy = "topics,publishDateYear",
                        filterBy = "",
                        maxFacetValues = 100,
                        page = 1,
                        perPage = 5
                    )
                )
            )
        }
    }
        .map {
            val results = it.results.getOrNull(0)?.hits ?: listOf()
            results.map { r -> r.document.toComic() }
        }
        .mapLeft {
            when (it) {
                is DataSourceException -> it
                else -> DataSourceException.ApiError(it)
            }
        }
}

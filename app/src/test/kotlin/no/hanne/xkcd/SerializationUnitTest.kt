package no.hanne.xkcd

import junit.framework.Assert.assertTrue
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.hanne.xkcd.core.models.xkcd.Search
import no.hanne.xkcd.core.models.xkcd.SearchComicRequest
import org.junit.Test

/**
 */
class SerializationUnitTest {
    @Test
    fun testSerialization() {
        val request = SearchComicRequest(
            listOf(
                Search(
                    query = "q",
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
        val str = Json.encodeToString(
            request
        )
        assertTrue(str.contains("collection"))
    }
}

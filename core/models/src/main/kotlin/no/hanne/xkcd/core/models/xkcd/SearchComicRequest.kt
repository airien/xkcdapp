package no.hanne.xkcd.core.models.xkcd

import kotlinx.serialization.SerialName

data class SearchComicRequest(
    val searches: List<Search>
)

data class Search(
    @SerialName("query_by")
    val queryBy: String = "title,altTitle,transcript,topics",
    @SerialName("query_by_weights")
    val queryByWeights: String = "127,80,80,1",
    @SerialName("drop_tokens_threshold")
    val dropTokensThreshold: Int = 2,
    @SerialName("typo_tokens_threshold")
    val typoTokensThreshold: Int = 0,
    @SerialName("num_typos")
    val numTypos: Int = 1,
    @SerialName("sort_by")
    val sortBy: String = "",
    @SerialName("highlight_full_fields")
    val highlightFullFields: String = "title,altTitle,transcript,topics",
    val collection: String = "xkcd",
    @SerialName("facet_by")
    val facetBy: String = "topics,publishDateYear",
    @SerialName("filter_by")
    val filterBy: String = "",
    @SerialName("max_facet_values")
    val maxFacetValues: Int = 100,
    val page: Int = 1,
    @SerialName("per_page")
    val perPage: Int = 5,
    @SerialName("q")
    val query: String
)

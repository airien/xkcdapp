package no.hanne.xkcd.core.models.xkcd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchComicRequest(
    val searches: List<Search>
)

@Serializable
data class Search(
    @SerialName("q")
    val query: String,
    @SerialName("query_by")
    val queryBy: String,
    @SerialName("query_by_weights")
    val queryByWeights: String,
    @SerialName("drop_tokens_threshold")
    val dropTokensThreshold: Int,
    @SerialName("typo_tokens_threshold")
    val typoTokensThreshold: Int,
    @SerialName("num_typos")
    val numTypos: Int,
    @SerialName("sort_by")
    val sortBy: String,
    @SerialName("highlight_full_fields")
    val highlightFullFields: String,
    @SerialName("collection")
    val collection: String,
    @SerialName("facet_by")
    val facetBy: String,
    @SerialName("filter_by")
    val filterBy: String,
    @SerialName("max_facet_values")
    val maxFacetValues: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int
)

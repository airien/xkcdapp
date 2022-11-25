package no.hanne.xkcd.core.models.xkcd

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val results: List<FoundObjects>
)

@Serializable
data class FoundObjects(
    val found: Int,
    val outOf: Int,
    val page: Int,
    val hits: List<ComicSearchResult>
)

@Serializable
data class ComicSearchResult(
    val id: Int,
    val publishDateDay: Int,
    val publishDateMonth: Int,
    val publishDateYear: Int,
    val publishDateTimestamp: Long,
    val altTitle: String?,
    val title: String?,
    val transcript: String?,
    val imageUrl: String?
) {
    fun toComic(): Comic {
        return Comic(
            num = id,
            day = publishDateDay.toString(),
            month = publishDateMonth.toString(),
            year = publishDateYear.toString(),
            title = title,
            safeTitle = title ?: "",
            alt = altTitle,
            img = imageUrl,
            link = null,
            transcript = transcript,
            news = null
        )
    }
}

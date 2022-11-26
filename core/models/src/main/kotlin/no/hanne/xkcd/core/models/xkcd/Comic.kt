package no.hanne.xkcd.core.models.xkcd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val num: Int,
    val day: String,
    val month: String,
    val year: String,
    val title: String?,
    val safeTitle: String?,
    val alt: String?,
    val img: String?,
    val link: String?,
    val transcript: String?,
    val news: String?
) {
    fun toDbComic(): DbComic {
        return DbComic(
            num = num,
            day = day,
            month = month,
            year = year,
            title = title,
            safeTitle = safeTitle,
            alt = alt,
            img = img,
            data = null, // fix this
            link = link,
            transcript = transcript,
            news = news
        )
    }
}

@Entity
data class DbComic(
    @PrimaryKey
    @ColumnInfo(name = "num", defaultValue = "0")
    val num: Int,
    val day: String,
    val month: String,
    val year: String,
    val title: String?,
    val safeTitle: String?,
    val alt: String?,
    val img: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val data: ByteArray?,
    val link: String?,
    val transcript: String?,
    val news: String?
) {
    fun toComic(): Comic {
        return Comic(
            num = num,
            day = day,
            month = month,
            year = year,
            title = title,
            safeTitle = safeTitle,
            alt = alt,
            img = img,
            link = link,
            transcript = transcript,
            news = news
        )
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DbComic

        if (num != other.num) return false
        if (day != other.day) return false
        if (month != other.month) return false
        if (year != other.year) return false
        if (title != other.title) return false
        if (safeTitle != other.safeTitle) return false
        if (alt != other.alt) return false
        if (img != other.img) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data.contentEquals(other.data)) return false
        } else if (other.data != null) return false
        if (link != other.link) return false
        if (transcript != other.transcript) return false
        if (news != other.news) return false

        return true
    }

    override fun hashCode(): Int {
        var result = num
        result = 31 * result + day.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + safeTitle.hashCode()
        result = 31 * result + (alt?.hashCode() ?: 0)
        result = 31 * result + (img?.hashCode() ?: 0)
        result = 31 * result + (data?.contentHashCode() ?: 0)
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + (transcript?.hashCode() ?: 0)
        result = 31 * result + (news?.hashCode() ?: 0)
        return result
    }
}

package no.hanne.xkcd.core.database

import androidx.room.TypeConverter
import java.time.Instant
import java.util.Date

object Converters {
    @TypeConverter
    fun storedStringToListOfString(value: String?): List<String> {
        return if (value.isNullOrEmpty() || value == "null") {
            listOf()
        } else {
            value.split("|")
                .toTypedArray()
                .toList()
        }
    }

    @TypeConverter
    fun listOfStringToString(value: List<String>?): String {
        return value?.joinToString(
            separator = "|"
        ) ?: ""
    }

    @JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @JvmStatic
    @TypeConverter
    fun toInstant(dateLong: Long?): Instant? {
        return if (dateLong == null) null else Instant.ofEpochSecond(dateLong)
    }

    @JvmStatic
    @TypeConverter
    fun fromInstant(date: Instant?): Long? {
        return date?.epochSecond
    }
}

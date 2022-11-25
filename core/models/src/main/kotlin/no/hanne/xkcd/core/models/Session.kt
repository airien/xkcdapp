package no.hanne.xkcd.core.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey
    @ColumnInfo(name = "sessionId", defaultValue = "")
    val id: String,
    val timestamp: String,
    val name: String
)

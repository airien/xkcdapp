package no.hanne.xkcd.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import no.hanne.xkcd.core.database.dao.ComicDao
import no.hanne.xkcd.core.models.xkcd.DbComic

@Database(
    entities = [DbComic::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class XKCDDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao

    companion object {
        @Volatile private var instance: XKCDDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            XKCDDatabase::class.java,
            "xkcd.db"
        )
            .build()
    }
}

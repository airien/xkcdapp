package no.hanne.xkcd.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import no.hanne.xkcd.core.models.xkcd.DbComic

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(comic: DbComic)

    @Query("SELECT * FROM DbComic")
    fun getLatest(): DbComic

    @Query("SELECT * FROM DbComic")
    fun get(): List<DbComic>

    @Query("SELECT * FROM DbComic WHERE num = :num")
    fun get(num: Int): DbComic?

    @Query("DELETE FROM DbComic")
    fun clear()

    @Query("DELETE FROM DbComic WHERE num = :num")
    fun delete(num: Int)

    @Update
    fun update(vararg comic: DbComic)
}

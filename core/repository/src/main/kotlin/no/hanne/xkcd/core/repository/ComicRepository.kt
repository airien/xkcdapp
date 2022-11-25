package no.hanne.xkcd.core.repository

import javax.inject.Inject
import no.hanne.xkcd.core.database.dao.ComicDao
import no.hanne.xkcd.core.exception.RepositoryException
import no.hanne.xkcd.core.models.network.Result
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.network.api.SearchApi
import no.hanne.xkcd.core.network.api.XkcdApi

interface ComicRepository {
    suspend fun getLatestComic(): Result<RepositoryException, Comic>
    suspend fun getComic(num: Int): Result<RepositoryException, Comic?>
    suspend fun searchComics(term: String): Result<RepositoryException, List<Comic>>
    fun storeComic(comic: Comic)
    fun getStoredComics(): List<Comic>
    fun clearComics()
    fun deleteComic(num: Int)
}

class ComicRepositoryImpl @Inject constructor(
    private val xkcdApi: XkcdApi,
    private val searchApi: SearchApi,
    private val comicDao: ComicDao,
    private val networkRequest: NetworkRequestHandler
) : ComicRepository {
    override suspend fun getLatestComic(): Result<RepositoryException, Comic> = networkRequest.run {
        xkcdApi.getLatestComic()
    }

    override suspend fun getComic(num: Int): Result<RepositoryException, Comic?> =
        networkRequest.run {
            xkcdApi.getComic(num)
        }

    override suspend fun searchComics(term: String): Result<RepositoryException, List<Comic>> =
        networkRequest.run {
            searchApi.search(term)
        }

    override fun storeComic(comic: Comic) {
        comicDao.save(comic.toDbComic())
    }

    override fun getStoredComics(): List<Comic> {
        return comicDao.get().map { it.toComic() }
    }

    override fun clearComics() {
        comicDao.clear()
    }

    override fun deleteComic(num: Int) {
        comicDao.delete(num)
    }
}

package no.hanne.xkcd.features.home

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import no.hanne.xkcd.ViewModelBase
import no.hanne.xkcd.ViewModelBaseImpl
import no.hanne.xkcd.core.models.network.NetworkingConstants
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.repository.ComicRepository
import no.hanne.xkcd.core.repository.DatastoreRepository
import kotlin.random.Random

interface HomeViewModel : ViewModelBase<HomeViewEffect> {
    val notifyNewComic: Boolean
    val explainLink: String?
    val isLastEnabled: Boolean
    val isNextEnabled: Boolean
    val isPreviousEnabled: Boolean
    val isFirstEnabled: Boolean
    val comic: Comic?
    val isLoading: Boolean
    val isFavourite: Boolean
    fun hideNotifyNewComic()
    fun previous()
    fun next()
    fun first()
    fun last()
    fun random()
    fun onSearch(term: String)
    fun setAsFavourite()
    fun removeAsFavourite()
    fun toggleFavourite()
}

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    override val resources: Resources,
    private val networkingConstants: NetworkingConstants,
    private val comicRepository: ComicRepository,
    private val datastoreRepository: DatastoreRepository
) : ViewModelBaseImpl<HomeViewEffect>(), HomeViewModel {
    private var latestComicStored: Int? = null
    private var latest: Comic? = null
    override val isLastEnabled: Boolean
        get() = (comic?.num ?: 0) < (latest?.num ?: 0)
    override val isNextEnabled: Boolean
        get() = (comic?.num ?: 0) < (latest?.num ?: 0)
    override val isPreviousEnabled: Boolean
        get() = (comic?.num ?: 0) > 1
    override val isFirstEnabled: Boolean
        get() = (comic?.num ?: 0) > 1
    override var isFavourite: Boolean by mutableStateOf(false)
    override var notifyNewComic: Boolean by mutableStateOf(false)
    override var explainLink: String? by mutableStateOf(null)
    override var comic: Comic? by mutableStateOf(null)
    override var isLoading: Boolean by mutableStateOf(true)

    init {
        viewModelScope.launch {
            latestComicStored = datastoreRepository.getLatest()

            comicRepository.latest.collect {
                if (it != null) {
                    if (latestComicStored != it.num) {
                        notifyNewComic = true
                    }
                    datastoreRepository.storeLatest(it.num)
                    latest = it
                    if (comic == null) updateComic(it)
                }
            }
        }
        runRequest({
            comicRepository.refreshLatest()
        }) {
            isLoading = false
        }
    }
    override fun hideNotifyNewComic() {
        notifyNewComic = false
    }

    override fun previous() {
        comic?.num?.let { number ->
            if (number > 0) {
                isLoading = true
                runRequest({ comicRepository.getComic(number - 1) }) {
                    updateComic(it)
                    isLoading = false
                }
            }
        }
    }

    override fun next() {
        comic?.num?.let { number ->
            if (number != latest?.num) {
                isLoading = true
                runRequest({ comicRepository.getComic(number + 1) }) {
                    updateComic(it)
                    isLoading = false
                }
            }
        }
    }

    override fun first() {
        isLoading = true
        runRequest({ comicRepository.getComic(1) }) {
            updateComic(it)
            isLoading = false
        }
    }

    override fun last() {
        comic = latest
    }

    override fun random() {
        isLoading = true
        val num = Random.nextInt(0, latest?.num ?: 2000)
        runRequest({ comicRepository.getComic(num) }) {
            updateComic(it)
            isLoading = false
        }
    }

    override fun onSearch(term: String) {
        sendViewEffect(HomeViewEffect.NavigateToSearch(term, latest?.num ?: 0))
    }

    override fun setAsFavourite() {
        comic?.let {
            viewModelScope.launch {
                datastoreRepository.storeFavourite(it.num)
            }
        }
    }

    override fun removeAsFavourite() {
        comic?.let {
            viewModelScope.launch {
                datastoreRepository.removeFavourite(it.num)
                isFavourite = false
            }
        }
    }

    override fun toggleFavourite() {
        comic?.let {
            if (isFavourite) removeAsFavourite()
            else setAsFavourite()
            isFavourite = true
        }
    }

    override fun onErrorDialogDismissed() {
        super.onErrorDialogDismissed()
        isLoading = false
    }

    private fun updateComic(it: Comic?) {
        checkIsFavourite(it)
        comic = it
        it?.let { c ->
            explainLink = "${networkingConstants.explainUrl}${c.num}:_${
            c.title?.replace(" ", "_")
            }"
        }
    }

    private fun checkIsFavourite(it: Comic?) {
        it?.let { comic ->
            viewModelScope.launch {
                val favourites = datastoreRepository.getFavourites()
                isFavourite = favourites.contains(comic.num)
            }
        }
    }
}
sealed class HomeViewEffect {
    data class NavigateToSearch(val term: String, val latest: Int) : HomeViewEffect()
}

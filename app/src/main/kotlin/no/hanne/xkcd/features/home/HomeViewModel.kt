package no.hanne.xkcd.features.home

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import no.hanne.xkcd.ViewModelBase
import no.hanne.xkcd.ViewModelBaseImpl
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.repository.ComicRepository

interface HomeViewModel : ViewModelBase<HomeViewEffect> {
    val comic: Comic?
    val isLoading: Boolean
}

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    override val resources: Resources,
    private val comicRepository: ComicRepository
) : ViewModelBaseImpl<HomeViewEffect>(), HomeViewModel {
    private var clicked: Int = 0
    override var comic: Comic? by mutableStateOf(null)
    override var isLoading: Boolean by mutableStateOf(true)

    init {
        runRequest({ comicRepository.getLatestComic() }) {
            comic = it
            isLoading = false
        }
    }

    override fun onErrorDialogDismissed() {
        super.onErrorDialogDismissed()
        isLoading = false
    }
}
sealed class HomeViewEffect

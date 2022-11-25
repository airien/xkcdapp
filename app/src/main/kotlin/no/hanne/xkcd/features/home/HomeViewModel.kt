package no.hanne.xkcd.features.home

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import no.hanne.xkcd.ViewModelBase
import no.hanne.xkcd.ViewModelBaseImpl
import no.hanne.xkcd.core.repository.ComicRepository
import javax.inject.Inject

interface HomeViewModel : ViewModelBase<HomeViewEffect> {
    val message: String?
    val isLoading: Boolean
}

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    override val resources: Resources,
    private val comicRepository: ComicRepository
) : ViewModelBaseImpl<HomeViewEffect>(), HomeViewModel {
    private var clicked: Int = 0
    override var message: String? by mutableStateOf(null)
    override var isLoading: Boolean by mutableStateOf(false)

    init {
        runRequest({ comicRepository.getLatestComic() }) {
            message = it.title
        }
    }

    override fun onErrorDialogDismissed() {
        super.onErrorDialogDismissed()
        isLoading = false
    }
}
sealed class HomeViewEffect

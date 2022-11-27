package no.hanne.xkcd.features.favourite

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
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.repository.ComicRepository
import no.hanne.xkcd.core.repository.DatastoreRepository
import no.hanne.xkcd.features.favourite.FavouriteViewEffect.NavigateBack

interface FavouriteViewModel : ViewModelBase<FavouriteViewEffect> {
    val isLoading: Boolean
    val result: List<Comic>
}

@HiltViewModel
class FavouriteViewModelImpl @Inject constructor(
    override val resources: Resources,
    private val comicRepository: ComicRepository,
    private val datastoreRepository: DatastoreRepository
) : ViewModelBaseImpl<FavouriteViewEffect>(), FavouriteViewModel {
    override var isLoading: Boolean by mutableStateOf(true)
    override var result: List<Comic> by mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            datastoreRepository.getFavourites().forEach {
                loadComic(it)
            }
            isLoading = false
        }
    }
    private fun loadComic(num: Int) {
        viewModelScope.launch {
            runRequestAsync({
                comicRepository.getComic(num)
            }).await()?.let { comic ->
                result = result.plus(comic)
            }
        }
    }
    override fun onErrorDialogDismissed() {
        super.onErrorDialogDismissed()
        sendViewEffect(NavigateBack)
    }
}

sealed class FavouriteViewEffect {
    object NavigateBack : FavouriteViewEffect()
}

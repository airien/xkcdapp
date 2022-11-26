package no.hanne.xkcd.features.search

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import no.hanne.xkcd.ViewModelBase
import no.hanne.xkcd.ViewModelBaseImpl
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.repository.ComicRepository
import no.hanne.xkcd.features.search.SearchViewEffect.NavigateBack

interface SearchViewModel : ViewModelBase<SearchViewEffect> {
    val isLoading: Boolean
    val result: List<Comic>
}

@HiltViewModel
class SearchViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    override val resources: Resources,
    private val comicRepository: ComicRepository
) : ViewModelBaseImpl<SearchViewEffect>(), SearchViewModel {
    private val term: String? = savedStateHandle["term"]
    private val latest: Int? = savedStateHandle["latest"]
    override var isLoading: Boolean by mutableStateOf(true)
    override var result: List<Comic> by mutableStateOf(listOf())

    init {
        term?.toIntOrNull()?.let {
            if (it > 0 && it <= (latest ?: 0)) {
                runRequest({
                    comicRepository.getComic(it)
                }) { res ->
                    if (res != null) result = listOf(res)
                    isLoading = false
                }
            }
        } ?: run {
            term?.let { t ->
                runRequest({
                    comicRepository.searchComics(t)
                }) { res ->
                    result = res
                    isLoading = false
                }
            }
        }
    }
    override fun onErrorDialogDismissed() {
        super.onErrorDialogDismissed()
        sendViewEffect(NavigateBack)
    }
}

sealed class SearchViewEffect {
    object NavigateBack : SearchViewEffect()
}

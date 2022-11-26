package no.hanne.xkcd.features.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.hanne.xkcd.R.string
import no.hanne.xkcd.core.ui.components.ErrorDialog
import no.hanne.xkcd.features.favourite.FavouriteViewEffect.NavigateBack
import no.hanne.xkcd.features.home.ComicArea

@Composable
fun FavouriteScreen(
    navController: NavController?,
    viewModel: FavouriteViewModel = hiltViewModel<FavouriteViewModelImpl>()
) {
    LaunchedEffect("view-effects") {
        viewModel.viewEffect.collect { viewEffect: FavouriteViewEffect ->
            when (viewEffect) {
                is NavigateBack -> {
                    navController?.popBackStack()
                }
            }
        }
    }
    ErrorDialog(
        errorMessageText = viewModel.errorMessage ?: "",
        dialogVisible = viewModel.errorMessage?.isNotEmpty() ?: false,
        onDialogDismissed = {
            viewModel.onErrorDialogDismissed()
        }
    )
    Box(
        modifier = Modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            when (viewModel.isLoading) {
                false -> {
                    LazyColumn() {
                        if (viewModel.result.isEmpty()) {
                            item {
                                Text(stringResource(id = string.no_result))
                            }
                        }
                        items(viewModel.result) { comic ->
                            ComicArea(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(vertical = 16.dp),
                                comic = comic,
                                null
                            )
                        }
                    }
                }
                true -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

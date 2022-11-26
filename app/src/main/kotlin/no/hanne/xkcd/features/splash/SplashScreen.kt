package no.hanne.xkcd.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.hanne.xkcd.core.ui.components.Animation
import no.hanne.xkcd.features.splash.SplashViewEffect.NavigateToHome
import no.hanne.xkcd.navigation.Route

@Composable fun SplashScreen(
    navController: NavController?,
    viewModel: SplashViewModel =
        hiltViewModel<SplashViewModelImpl>()
) {
    LaunchedEffect("view-effects") {
        viewModel.viewEffect.collect { viewEffect: SplashViewEffect ->
            when (viewEffect) {
                is NavigateToHome -> {
                    navController?.navigate(Route.Home.destination)
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.initialize()
    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Animation()
    }
}

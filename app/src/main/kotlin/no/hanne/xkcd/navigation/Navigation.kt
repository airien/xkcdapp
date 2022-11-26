package no.hanne.xkcd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.hanne.xkcd.R.string
import no.hanne.xkcd.core.ui.components.ErrorDialog
import no.hanne.xkcd.features.home.HomeScreen
import no.hanne.xkcd.features.search.SearchScreen
import no.hanne.xkcd.features.splash.SplashScreen
import no.hanne.xkcd.navigation.NavigationViewEffect.NavigateToDeeplink
import no.hanne.xkcd.navigation.NavigationViewEffect.NavigateToRoute
import no.hanne.xkcd.navigation.Route.Home
import no.hanne.xkcd.navigation.Route.Splash

@Composable
fun Navigation(
    viewModel: NavigationViewModel =
        hiltViewModel<NavigationViewModelImpl>()
) {
    val navController = rememberNavController()
    LaunchedEffect("view-effects") {
        viewModel.viewEffect.collect { viewEffect: NavigationViewEffect ->
            when (viewEffect) {
                is NavigateToDeeplink -> {
                    navController.navigate(viewEffect.route)
                }
                is NavigateToRoute ->
                    navController.navigate(viewEffect.route)
            }
        }
    }
    ErrorDialog(
        errorTitleText = stringResource(id = string.error_dialog_title_ooops),
        errorMessageText = viewModel.errorMessage ?: "",
        dialogVisible = viewModel.errorMessage?.isNotEmpty() == true,
        onDialogDismissed = {
            viewModel.onErrorDialogDismissed()
        }
    )
    NavHost(
        navController = navController,
        startDestination = Splash.destination
    ) {
        composable(Home.destination) {
            HomeScreen(navController)
        }

        composable(Splash.destination) {
            SplashScreen(navController)
        }

        composable(
            route = "${Route.Search.destination}{term}/{latest}",
            arguments = listOf(
                navArgument("term") { type = NavType.StringType },
                navArgument("latest") { type = NavType.IntType }
            )
        ) {
            SearchScreen(navController)
        }
    }
}

package no.hanne.xkcd.navigation

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import no.hanne.xkcd.ViewModelBase
import no.hanne.xkcd.ViewModelBaseImpl
import no.hanne.xkcd.core.ui.components.NavigationTarget
import no.hanne.xkcd.navigation.Route.Home
import javax.inject.Inject
import no.hanne.xkcd.R.drawable

interface NavigationViewModel : ViewModelBase<NavigationViewEffect> {
    val menuItems: List<NavigationTarget>
    fun isSelectedRoute(currentRoute: String, route: String): Boolean
}

@HiltViewModel
class NavigationViewModelImpl @Inject constructor(
    override val resources: Resources
) :
    ViewModelBaseImpl<NavigationViewEffect>(),
    NavigationViewModel {
    override var menuItems: List<NavigationTarget> by mutableStateOf(
        listOf(
            NavigationTarget(
                route = Home.destination,
                iconRes = drawable.hppy_unicorn
            )
        )
    )

    override fun isSelectedRoute(currentRoute: String, route: String): Boolean {
        return when (currentRoute) {
            Home.destination -> return route == Home.destination
            else -> false
        }
    }
}

sealed class NavigationViewEffect {
    data class NavigateToDeeplink(val route: String) : NavigationViewEffect()
    data class NavigateToRoute(val route: String) : NavigationViewEffect()
    object NavigateToLogin : NavigationViewEffect()
}

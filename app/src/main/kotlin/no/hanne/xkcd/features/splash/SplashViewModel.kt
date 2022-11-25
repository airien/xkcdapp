package no.hanne.xkcd.features.splash

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.hanne.xkcd.ViewModelBase
import no.hanne.xkcd.ViewModelBaseImpl
import no.hanne.xkcd.features.splash.SplashViewEffect.NavigateToHome
import javax.inject.Inject

interface SplashViewModel : ViewModelBase<SplashViewEffect> {
    fun initialize()
}

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    override val resources: Resources
) :
    ViewModelBaseImpl<SplashViewEffect>(),
    SplashViewModel {
    override fun initialize() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(1300)
                sendViewEffect(NavigateToHome)
            }
        }
    }
}

sealed class SplashViewEffect {
    object NavigateToHome : SplashViewEffect()
}

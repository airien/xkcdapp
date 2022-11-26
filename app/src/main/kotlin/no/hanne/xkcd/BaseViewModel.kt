package no.hanne.xkcd

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import no.hanne.xkcd.core.exception.RepositoryException
import no.hanne.xkcd.core.models.network.Result
import no.hanne.xkcd.core.network.Requester
import timber.log.Timber

interface ViewModelBase<T> {
    val viewEffect: Flow<T>
    val errorMessage: String?
    fun onErrorDialogDismissed()
}

abstract class ViewModelBaseImpl<T> : ViewModelBase<T>, ViewModel() {
    private val localViewEffect = Channel<T>()
    override val viewEffect: Flow<T> = localViewEffect.receiveAsFlow()
    override var errorMessage: String? by mutableStateOf(null)
        protected set
    protected abstract val resources: Resources
    private val requester: Requester = Requester(
        scope = viewModelScope,
        onHandleError = {
            handleError(it)
        }
    )
    protected open fun handleError(error: Throwable?) {
        // We don't want to show user a tech error. So, log it and show very generic message.
        // Timber will in production builds send error to crashlytics automatically
        Timber.e(error)
        errorMessage = resources.getString(R.string.general_error_message)
    }
    protected fun sendViewEffect(effect: T) {
        viewModelScope.launch {
            localViewEffect.send(effect)
        }
    }

    fun <R> runRequest(
        onRunCall: suspend () -> Result<RepositoryException, R>,
        onError: (Throwable?) -> Unit = {},
        scope: CoroutineScope = viewModelScope,
        onGotValue: (value: R) -> Unit = {}
    ): Job {
        return requester.runRequest(
            onRunCall = onRunCall,
            onError = onError,
            scope = scope,
            onGotValue = onGotValue
        )
    }

    fun <T> runRequestAsync(
        onRunCall: suspend () -> Result<RepositoryException, T>,
        onError: (Throwable?) -> Unit = {},
        scope: CoroutineScope = viewModelScope
    ): Deferred<T?> {
        return requester.runRequestAsync(
            onRunCall = onRunCall,
            onError = onError,
            scope = scope
        )
    }

    override fun onErrorDialogDismissed() {
        errorMessage = null
    }
}

sealed class EmptyViewEffect

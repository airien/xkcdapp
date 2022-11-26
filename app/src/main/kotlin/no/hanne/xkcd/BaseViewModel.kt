package no.hanne.xkcd

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.hanne.xkcd.core.models.network.Result
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

    protected fun <T> runRequest(
        onRunCall: suspend () -> Result<Throwable, T>,
        onError: (Throwable?) -> Unit = {},
        scope: CoroutineScope = viewModelScope,
        onGotValue: (value: T) -> Unit = {}
    ): Job {
        return makeRequest(
            scope,
            onRunCall,
            {
                handleError(it)
                onError(it)
            },
            onGotValue
        )
    }
    fun <T> makeRequest(
        scope: CoroutineScope,
        onRunCall: suspend () -> Result<Throwable, T>,
        handleError: (value: Throwable?) -> Unit = {},
        onGotValue: (value: T) -> Unit = {},
    ): Job {
        return scope.launch(Dispatchers.IO) {
            when (val result = onRunCall()) {
                is Result.Success<T> -> {
                    withContext(Dispatchers.Main) {
                        onGotValue(result.value)
                    }
                }
                is Result.Failure -> {
                    withContext(Dispatchers.Main) {
                        handleError(result.error)
                    }
                }
            }
        }
    }

    override fun onErrorDialogDismissed() {
        errorMessage = null
    }
}

sealed class EmptyViewEffect

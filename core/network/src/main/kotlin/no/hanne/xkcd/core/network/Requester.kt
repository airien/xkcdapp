package no.hanne.xkcd.core.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.hanne.xkcd.core.exception.RepositoryException
import no.hanne.xkcd.core.models.network.Result


open class Requester(
    private val scope: CoroutineScope,
    private val onHandleError: (throwable: Throwable?) -> Unit
) {
    fun <R> runRequest(
        onRunCall: suspend () -> Result<RepositoryException, R>,
        scope: CoroutineScope = this.scope,
        onError: (Throwable?) -> Unit = {},
        onGotValue: (value: R) -> Unit = {}
    ): Job {
        return makeRequest(
            scope,
            onRunCall,
            {
                onHandleError(it)
                onError(it)
            },
            onGotValue
        )
    }

    private fun <T> makeRequest(
        scope: CoroutineScope,
        onRunCall: suspend () -> Result<RepositoryException, T>,
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

    fun <T> runRequestAsync(
        onRunCall: suspend () -> Result<RepositoryException, T>,
        scope: CoroutineScope = this.scope,
        onError: (Throwable?) -> Unit = {},
    ): Deferred<T?> {
        return makeRequestAsync(
            scope,
            onRunCall
        ) {
            onHandleError(it)
            onError(it)
        }
    }
    private fun <T> makeRequestAsync(
        scope: CoroutineScope,
        onRunCall: suspend () -> Result<RepositoryException, T>,
        handleError: (value: Throwable?) -> Unit = {},
    ): Deferred<T?> {
        return scope.async(Dispatchers.IO) {
            when (val result = onRunCall()) {
                is Result.Success<T> -> {
                    withContext(Dispatchers.Main) {
                        result.value
                    }
                }
                is Result.Failure -> {
                    withContext(Dispatchers.Main) {
                        handleError(result.error)
                        null
                    }
                }
            }
        }
    }
}

package no.hanne.xkcd.core.models.network

sealed class Result<out E, out R> {
    data class Failure<E>(val error: E) : Result<E, Nothing>()
    data class Success<R>(val value: R) : Result<Nothing, R>()
}

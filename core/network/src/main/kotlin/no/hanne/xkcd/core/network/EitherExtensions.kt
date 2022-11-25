package no.hanne.xkcd.core.network // ktlint-disable filename

import arrow.core.Either
import no.hanne.xkcd.core.models.network.Result

fun <L, R> Either<L, R>.toResult(): Result<L, R> {
    return fold(ifLeft = { Result.Failure(it) }, ifRight = { Result.Success(it) })
}

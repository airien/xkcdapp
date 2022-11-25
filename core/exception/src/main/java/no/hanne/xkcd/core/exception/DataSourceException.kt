package no.hanne.xkcd.core.exception

import android.content.res.Resources
import arrow.core.Either
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.ServerResponseException
import kotlinx.serialization.SerializationException

sealed class DataSourceException(cause: Throwable) : Exception(cause) {
    /**
     * Exception raised when we were unable to reach the destination. This is most likely because the user is on a
     * bad Internet connection or none at all. More details are found in its [reason].
     */
    class ConnectivityError(val reason: Throwable) : DataSourceException(reason)

    /**
     * Exception raised when we receive a 4xx response from a back-end API call. More details are its [reason].
     */
    class ClientError(val reason: ClientRequestException) : DataSourceException(reason)

    /**
     * Exception raised when we receive a 5xx response from a back-end API call. More details are its [reason]
     */
    class ServerError(val reason: ServerResponseException) : DataSourceException(reason)

    /**
     * Exception raised when we received a response, but were unable to parse it. This typically means that our expected
     * response type cannot be constructed from the response we received. More details are in its [reason].
     */
    class ParseResponseError(val reason: SerializationException) : DataSourceException(reason)

    /**
     * Exception raised when we could not find the element we asked for
     * response type cannot be constructed from the response we received. More details are in its [reason].
     */
    class ElementNotFoundError(val reason: Resources.NotFoundException) : DataSourceException(reason)

    /**
     * Exception raised when something goes wrong in an API
     * Api code did not run. More details are in its [reason].
     */
    class ApiError(val reason: Throwable) : DataSourceException(reason)

    /**
     * Exception raised when something is wrong with user token, or user isn't logged in
     * User not authenticated. More details are in its [reason].
     */
    class UserLoggedOutError(val reason: Throwable) : DataSourceException(reason)

    /**
     * Exception raised when we could not find the element we asked for
     * response type cannot be constructed from the response we received. More details are in its [reason].
     */
    class FirebaseConnectionError(val reason: Throwable) : DataSourceException(reason)

    /**
     * Exception raised when anything else crashes
     * Something goes wrong. More details are in its [reason].
     */
    class OtherError(val reason: Throwable) : DataSourceException(reason)

    object AuthenticationError : DataSourceException(Throwable())
}

typealias DataSourceResult<R> = Either<DataSourceException, R>

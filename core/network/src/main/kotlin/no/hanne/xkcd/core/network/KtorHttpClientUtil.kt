package no.hanne.xkcd.core.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.features.cache.HttpCache
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.ExperimentalWebSocketExtensionApi
import io.ktor.http.cio.websocket.WebSocketDeflateExtension
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import no.hanne.xkcd.core.exception.DataSourceException
import no.hanne.xkcd.core.networking.TrustAllX509TrustManager
import no.hanne.xkcd.core.util.LanguageUtils
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.SecureRandom
import java.text.ParseException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeoutException
import java.util.zip.Deflater

object KtorHttpClientUtil {
    fun getHttpClient(
        enableLogging: Boolean,
        userAgent: String,
        json: Json,
        device: String
    ) = HttpClient(CIO) {
        expectSuccess = false
        engine {
            https {
                trustManager = TrustAllX509TrustManager()
                random = SecureRandom()
            }
        }

        install(HttpCache) {}
        installTimeout()
        installEnableLogging(enableLogging)
        installJson(json)
        defaultRequest {
            if (body !is FormDataContent && body !is MultiPartFormDataContent) {
                contentType(ContentType.Application.Json)
            }
            header("User-Agent", userAgent)
            header("Accept-Language", LanguageUtils.languageLocaleCode)
            header("Device", device)
        }

        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value
                val description = response.status.description

                Timber.d("HTTP status: $statusCode")
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response, description)
                    in 401..403 -> throw ServerResponseException(response, description) // catches unauthorized and forbidden 401 and 403
                    in 400..499 -> throw ClientRequestException(response, description)
                    in 500..599 -> throw ServerResponseException(response, description)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response, description)
                }
            }
            handleResponseException {
                when (it) {
                    is DataSourceException -> {
                        Timber.e(it)
                        throw it
                    }
                    is UnresolvedAddressException -> {
                        Timber.e(it)
                        throw DataSourceException.ConnectivityError(it)
                    }
                    is ClientRequestException -> {
                        Timber.w(it)
                        throw DataSourceException.ClientError(it)
                    }
                    is ServerResponseException -> {
                        Timber.e(it)
                        throw DataSourceException.ServerError(it)
                    }
                    is SerializationException -> {
                        Timber.e(it)
                        throw DataSourceException.ParseResponseError(it)
                    }
                    is UnknownHostException -> {
                        Timber.e(it)
                        throw DataSourceException.ConnectivityError(it)
                    }
                    is SocketTimeoutException -> {
                        Timber.w(it)
                        throw DataSourceException.ConnectivityError(it)
                    }
                    is TimeoutException -> {
                        Timber.w(it)
                        throw DataSourceException.ConnectivityError(it)
                    }
                    is ParseException -> {
                        throw DataSourceException.OtherError(it)
                    }
                    else -> {
                        if (it is CancellationException) {
                            Timber.w(it)
                        } else {
                            Timber.e(it)
                        }
                        throw DataSourceException.OtherError(it)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getJson() = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
        isLenient = true
        useArrayPolymorphism = true
    }

    private fun HttpClientConfig<CIOEngineConfig>.installTimeout() {
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
    }

    @ExperimentalWebSocketExtensionApi
    private fun HttpClientConfig<CIOEngineConfig>.installCompression() {
        install(WebSockets) {
            extensions {
                install(WebSocketDeflateExtension) {
                    compressionLevel = Deflater.DEFAULT_COMPRESSION
                    compressIfBiggerThan(bytes = 4 * 1024)
                }
            }
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.installEnableLogging(
        enableLogging: Boolean
    ) {
        if (enableLogging) {
            install(Logging) {
                logger = CustomNetworkLogger()
                level = LogLevel.ALL
            }
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.installJson(json: Json) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json
            )
        }
    }
}

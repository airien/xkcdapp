package no.hanne.xkcd.modules

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.util.InternalAPI
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import no.hanne.xkcd.BuildConfig
import no.hanne.xkcd.core.models.network.NetworkingConstants
import no.hanne.xkcd.core.network.KtorHttpClientUtil
import no.hanne.xkcd.core.network.api.SearchApi
import no.hanne.xkcd.core.repository.NetworkRequestHandler

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkingModule {
    @Provides
    @Singleton
    fun provideJson() = KtorHttpClientUtil.getJson()

    @ExperimentalSerializationApi
    @InternalAPI
    @Suppress("ThrowsCount", "LongMethod")
    @Provides
    @Singleton
    fun provideKtorHttpClient(
        json: Json,
        networkingConstants: NetworkingConstants
    ) = KtorHttpClientUtil.getHttpClient(
        userAgent = "Android/1.0.0",
        enableLogging = networkingConstants.enableLogging,
        json = json,
        device = "companion"
    )

    @Provides
    @Singleton
    fun provideSearchApi(
        client: HttpClient,
        networkingConstants: NetworkingConstants
    ) = SearchApi(client, networkingConstants)

    @Provides
    @Singleton
    fun provideNetworkingConstants(): NetworkingConstants {
        return NetworkingConstants(
            debug = BuildConfig.DEBUG,
            applicationId = BuildConfig.APPLICATION_ID,
            buildType = BuildConfig.BUILD_TYPE,
            versionCode = BuildConfig.VERSION_CODE,
            versionName = BuildConfig.VERSION_NAME,
            enableLogging = BuildConfig.ENABLE_LOGGING,
            xkcdUrl = BuildConfig.XKCD_JSON_URL,
            explainUrl = BuildConfig.XKCD_EXPLAIN_URL,
            typesenceUrl = BuildConfig.TYPESENSE_URL,
            typesenceUrlAppKey = BuildConfig.TYPESENSE_API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideNetworkRequestHandler(
        resources: Resources
    ) = NetworkRequestHandler(
        resources = resources
    )
}

package no.hanne.xkcd.core.network

import io.ktor.client.features.logging.Logger
import timber.log.Timber

class CustomNetworkLogger() : Logger {
    override fun log(message: String) {
        Timber.d(message)
    }
}

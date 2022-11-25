package no.hanne.xkcd.core.exception

import android.content.Context
import timber.log.Timber

object ExceptionUtil {
    fun setExceptionHandler(context: Context, debug: Boolean) {
        if (debug) {
            Thread.setDefaultUncaughtExceptionHandler(
                UncaughtExceptionHandler(
                    context,
                    Thread.getDefaultUncaughtExceptionHandler()
                )
            )
        }
    }

    fun setUpTimber(debug: Boolean) {
        if (debug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

package no.hanne.xkcd

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import no.hanne.xkcd.core.exception.ExceptionUtil
import timber.log.Timber

@HiltAndroidApp
class XKCDApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ExceptionUtil.setExceptionHandler(this, BuildConfig.DEBUG)
        ExceptionUtil.setUpTimber(BuildConfig.DEBUG)
        Timber.d("App created")
    }
}

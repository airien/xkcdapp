package no.hanne.xkcd.core.util

import android.content.Context
import android.content.Intent
import timber.log.Timber

object ShareUtils {
    fun shareLinkToOthers(context: Context, text: String?, title: String?) {
        val chooserIntent = Intent(Intent.ACTION_SEND)
        chooserIntent.type = "text/plain"
        title?.let {
            chooserIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                it
            )
        }
        text?.let {
            chooserIntent.putExtra(
                Intent.EXTRA_TEXT,
                it
            )
        }
        try {
            context.startActivity(
                Intent.createChooser(chooserIntent, title ?: "XKCD")
            )
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}

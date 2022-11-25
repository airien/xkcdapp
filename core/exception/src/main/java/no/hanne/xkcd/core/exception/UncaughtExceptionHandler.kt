package no.hanne.xkcd.core.exception

import android.content.Context
import android.content.Intent
import no.hanne.xkcd.core.exception.visualizer.ExceptionActivity
import timber.log.Timber
import kotlin.system.exitProcess

class UncaughtExceptionHandler(
    private val context: Context,
    private val default: Thread.UncaughtExceptionHandler?,
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, error: Throwable) {
        if (BuildConfig.DEBUG) { // just never start unless debug app
            Timber.e(error)
            val report = createReport(error, true)
            val strReport = report.toString()
            startExceptionActivity(context, strReport)
            default?.uncaughtException(thread, error)
        }
    }

    private fun startExceptionActivity(context: Context, report: String) {
        val intent = Intent(context, ExceptionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Timber.d("showing report $report")
        intent.putExtra(IntentExtrasNames.report, report)
        val packageName = context.applicationContext.packageName
        intent.putExtra(IntentExtrasNames.packageName, packageName)
        context.startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }

    internal companion object IntentExtrasNames {
        const val report = "report"
        const val packageName = "packageName"
    }
}

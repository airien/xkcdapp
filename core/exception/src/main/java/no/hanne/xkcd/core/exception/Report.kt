package no.hanne.xkcd.core.exception

import android.os.Build
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class Report(val traces: List<Stacktrace>) : java.io.Serializable {
    val metaData: MutableList<ExceptionMetadata> = mutableListOf()

    fun addMetaData(key: String, value: Any) {
        metaData.removeAll { it.key == key }
        metaData.add(ExceptionMetadata(key, value.toString()))
    }

    override fun toString(): String {
        return Json.encodeToString(this)
    }
}

fun createReport(exc: Throwable, fatal: Boolean): Report {
    val trace = exc.stackTrace.map { trace ->
        Stacktrace(trace.fileName, trace.lineNumber, trace.methodName)
    }

    val report = Report(trace)
    report.addMetaData("Message", exc.localizedMessage ?: exc.message ?: "No message")
    report.addMetaData("FATAL", fatal)
    report.addMetaData("Available memory", Runtime.getRuntime().freeMemory())
    report.addMetaData("OSVERSION", System.getProperty("os.version") ?: "")
    report.addMetaData("RELEASE", Build.VERSION.RELEASE ?: "")
    report.addMetaData("DEVICE", Build.DEVICE ?: "")
    report.addMetaData("MODEL", Build.MODEL ?: "")
    report.addMetaData("PRODUCT", Build.PRODUCT ?: "")
    report.addMetaData("BRAND", Build.BRAND ?: "")
    report.addMetaData("DISPLAY", Build.DISPLAY ?: "")
    report.addMetaData("UNKNOWN", Build.UNKNOWN)
    report.addMetaData("HARDWARE", Build.HARDWARE ?: "")
    report.addMetaData("ID", Build.ID ?: "")
    report.addMetaData("MANUFACTURER", Build.MANUFACTURER ?: "")
    report.addMetaData("USER", Build.USER ?: "")
    report.addMetaData("HOST", Build.HOST ?: "")

    return report
}

package no.hanne.xkcd.core.exception.visualizer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import no.hanne.xkcd.core.exception.R
import no.hanne.xkcd.core.exception.Report
import no.hanne.xkcd.core.exception.Stacktrace
import no.hanne.xkcd.core.exception.UncaughtExceptionHandler
import timber.log.Timber

class ExceptionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val reportStr = intent.getStringExtra(UncaughtExceptionHandler.report) ?: "Mentra"
        val packageName = intent.getStringExtra(UncaughtExceptionHandler.packageName)
        val report: Report = Json.decodeFromString(reportStr)
        Timber.d("Caught exception: $reportStr")
        setContent {
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ErrorVisualiser(report, packageName)
                }
            }
        }
        Timber.d("MainActivity created")
    }
}

@Composable
fun ErrorVisualiser(
    report: Report,
    packageName: String?
) {
    val context = LocalContext.current
    Image(
        painter = painterResource(R.drawable.crying),
        contentDescription = "Crying android",
        Modifier.fillMaxWidth(),
        alpha = 0.5f
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("$packageName crashed horrifically and died")
        if (packageName != null) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val launchIntent: Intent? = context
                        .packageManager
                        .getLaunchIntentForPackage(packageName)
                    if (launchIntent != null) {
                        context.startActivity(launchIntent) // null pointer check in case package name was not found
                        android.os.Process.killProcess(android.os.Process.myPid())
                        System.exit(0)
                    }
                }
            ) {
                Text(text = "Restart")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    Column(
        modifier = Modifier
            .offset(y = 100.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        report.metaData.map {
            Text(
                "${it.key} - ${it.value}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Stacktrace:",
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Left
            )
        )
        report.traces.map { trace ->
            Text(
                "file: ${trace.file} ln ${trace.line} - ${trace.method}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        ErrorVisualiser(
            Report(
                listOf(
                    Stacktrace(
                        "file",
                        22,
                        "method"
                    )
                )
            ),
            "test"
        )
    }
}

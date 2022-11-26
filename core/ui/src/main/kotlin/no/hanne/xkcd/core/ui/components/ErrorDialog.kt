package no.hanne.xkcd.core.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import no.hanne.xkcd.core.ui.R

@Composable
fun ErrorDialog(
    dialogVisible: Boolean,
    errorTitleText: String = stringResource(id = R.string.error_dialog_title_ooops),
    errorMessageText: String = stringResource(id = R.string.general_error_message),
    errorConfirmButtonText: String = stringResource(id = R.string.error_dialog_button_ok),
    onDialogDismissed: () -> Unit
) {
    if (dialogVisible) {
        AlertDialog(
            onDismissRequest = {
                onDialogDismissed()
            },
            title = {
                Text(
                    text = errorTitleText,
                    color = MaterialTheme.colors.primary
                )
            },
            text = {
                Text(
                    text = errorMessageText,
                    color = MaterialTheme.colors.primary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDialogDismissed()
                    }
                ) { Text(errorConfirmButtonText) }
            }
        )
    }
}

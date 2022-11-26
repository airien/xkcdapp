package no.hanne.xkcd.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("LongParameterList")
@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxCharacters: Int = Int.MAX_VALUE,
    validationErrorString: String = "",
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colors.primary,
    )
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { if (it.length <= maxCharacters) onValueChange(it) },
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            label = { TextFieldLabel(label = labelText) },
            colors = colors,
            isError = validationErrorString.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (validationErrorString.isNotEmpty()) {
            Text(text = validationErrorString, color = MaterialTheme.colors.error)
        }
    }
}

@Composable
fun TextFieldLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.body2
    )
}

package no.hanne.xkcd.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import no.hanne.xkcd.R.string
import no.hanne.xkcd.core.ui.components.AppButton
import no.hanne.xkcd.core.ui.components.AppOutlinedTextField

@Composable
fun SearchControl(
    modifier: Modifier,
    onSearch: (term: String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppOutlinedTextField(
            labelText = "",
            singleLine = true,
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { value = it }
        )
        AppButton(
            text = stringResource(id = string.search)
        ) {
            if (value.isNotEmpty()) onSearch(value)
        }
    }
}
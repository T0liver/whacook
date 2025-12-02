package hu.toliver.whacook.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Subheader(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
    )
}
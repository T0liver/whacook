package hu.toliver.whacook.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Header(
    text: String = "WhaCOOK?",
) {
    Text(text, style = MaterialTheme.typography.headlineSmall)
}
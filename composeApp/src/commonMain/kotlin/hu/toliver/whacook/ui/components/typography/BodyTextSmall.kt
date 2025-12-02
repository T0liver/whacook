package hu.toliver.whacook.ui.components.typography

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun BodyTextSmall(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    color : Long = 0xFF999999
) {
    Text(
        text = text,
        style = style,
        color = Color(color)
    )
}
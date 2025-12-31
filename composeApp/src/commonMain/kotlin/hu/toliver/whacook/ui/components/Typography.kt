package hu.toliver.whacook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BodyText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = text,
        style = style
    )
}

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

@Composable
fun BodyTextUnderline(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit = {},
){
    Text(
        text = text,
        style = style,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .clickable(onClick = onClick)
    )
}

@Preview
@Composable
fun Header(
    text: String = "WhaCOOK?",
) {
    Text(text,
        style = MaterialTheme.typography.headlineLarge
    )
}
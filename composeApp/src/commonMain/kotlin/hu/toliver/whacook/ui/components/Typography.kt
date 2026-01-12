package hu.toliver.whacook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BodyText(
    text: String,
    fontSize: TextUnit = 16.sp,
    color: Color = Color.Black
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color
    )
}

@Composable
fun BodyTextHyperlink(
    text: String,
    url: String,
    fontSize: TextUnit = 16.sp,
    color: Color = Color.Blue
) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            uriHandler.openUri(url)
        }
    )
}

@Composable
fun BodyTextHeader(
    text: String,
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Color.Black
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color
    )
}

@Composable
fun BodyTextSmall(
    text: String,
    fontSize: TextUnit = 14.sp,
    color : Color = Color(0xFF999999)
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontStyle = FontStyle.Italic,
        color = color,
        modifier = Modifier
            .padding(horizontal = 32.dp)
    )
}

@Composable
fun BodyTextUnderline(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontStyle: FontStyle = FontStyle.Italic,
    fontSize: TextUnit = 14.sp,
    color : Color = Color(0xFF999999),
    onClick: () -> Unit = {},
){
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        fontStyle = fontStyle,
        textDecoration = TextDecoration.Underline,
        color = color,
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
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Subheader(
    text: String,
) {
    Column {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 4.dp)
        )
        Separator()
        Spacer(Modifier.height(8.dp))
    }
}
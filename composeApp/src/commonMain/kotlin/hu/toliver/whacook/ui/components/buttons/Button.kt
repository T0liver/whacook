package hu.toliver.whacook.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.toliver.whacook.ui.theme.LightColors

@Composable
fun PButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val colors = LightColors
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightColors.primaryButton,
            contentColor = colors.onPrimaryButton,
        ),
        border = BorderStroke(2.dp, colors.primary),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text)
    }
}

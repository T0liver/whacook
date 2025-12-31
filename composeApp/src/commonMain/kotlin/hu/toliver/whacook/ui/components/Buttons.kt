package hu.toliver.whacook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.back

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
            containerColor = colors.primaryButton,
            contentColor = colors.onPrimaryButton,
        ),
        border = BorderStroke(2.dp, colors.stroke),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text)
    }
}

@Composable
fun BackButton() {
    val navigator = LocalNavigator.currentOrThrow
    IconButton(
        onClick = { navigator.pop() }
    ) {
        Icon(
            painter = painterResource(Res.drawable.back),
            contentDescription = "Back"
        )
    }
}
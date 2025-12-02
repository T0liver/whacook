package hu.toliver.whacook.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.back

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
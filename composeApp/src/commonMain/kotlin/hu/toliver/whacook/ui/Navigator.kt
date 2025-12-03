package hu.toliver.whacook.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun Navigation(navigator: Navigator) {
    Scaffold {
        padding ->
        Box(modifier = Modifier.padding(padding)) {
            navigator.lastItem.Content()
        }
    }
}
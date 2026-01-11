package hu.toliver.whacook

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import hu.toliver.whacook.data.di.startAppKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startAppKoin()
    ComposeViewport {
        App()
    }
}
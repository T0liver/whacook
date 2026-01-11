package hu.toliver.whacook

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hu.toliver.whacook.data.di.startAppKoin

fun main() = application {
    startAppKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "whacook",
    ) {
        App()
    }
}
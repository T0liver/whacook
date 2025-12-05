package hu.toliver.whacook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import hu.toliver.whacook.data.di.initKoin
import hu.toliver.whacook.ui.screens.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        Navigator(HomeScreen())
    }
}

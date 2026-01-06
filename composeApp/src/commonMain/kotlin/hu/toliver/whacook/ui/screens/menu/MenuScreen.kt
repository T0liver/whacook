package hu.toliver.whacook.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.PButton

class MenuScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<MenuScreenViewModel>()
        MenuScreenContent(viewModel.state)
    }
}

@Composable
fun MenuScreenContent(
    state: MenuState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Header("Settings")
            
            Spacer(Modifier.height(100.dp))
        }
    }
}

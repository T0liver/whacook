package hu.toliver.whacook.ui.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import hu.toliver.whacook.ui.components.BodyTextSmall
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.MenuElement
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.about
import whacook.composeapp.generated.resources.feedback
import whacook.composeapp.generated.resources.key

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
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Header("Settings")
            Spacer(Modifier.height(20.dp))
            MenuElement("API Key", Res.drawable.key)
            Spacer(Modifier.height(16.dp))
            MenuElement("Feedback", Res.drawable.feedback)
            Spacer(Modifier.height(16.dp))
            MenuElement("About", Res.drawable.about)
            Spacer(Modifier.height(16.dp))
            BodyTextSmall("In the design the icons are provided by icons8.com. Icons by Icons8.")
            Spacer(Modifier.height(100.dp))
        }
    }
}

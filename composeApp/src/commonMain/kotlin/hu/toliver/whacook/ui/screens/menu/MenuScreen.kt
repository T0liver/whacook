package hu.toliver.whacook.ui.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.components.BodyTextSmall
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.MenuElement
import hu.toliver.whacook.ui.screens.about.AboutScreen
import hu.toliver.whacook.ui.screens.apikey.APIKeyScreen
import hu.toliver.whacook.ui.screens.feedback.FeedbackScreen
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
    @Suppress("UNUSED_PARAMETER")
    state: MenuState
) {
    val navigator = LocalNavigator.currentOrThrow
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = screenHeight),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Header("Settings")
                    Spacer(Modifier.height(20.dp))
                    MenuElement("API Key", Res.drawable.key) {
                        navigator.push(APIKeyScreen())
                    }
                    Spacer(Modifier.height(16.dp))
                    MenuElement("Feedback", Res.drawable.feedback) {
                        navigator.push(FeedbackScreen())
                    }
                    Spacer(Modifier.height(16.dp))
                    MenuElement("About", Res.drawable.about) {
                        navigator.push(AboutScreen())
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BodyTextSmall("In the design the icons are provided by icons8.com. Icons by Icons8.")
                    Spacer(Modifier.height(120.dp))
                }
            }
        }
    }
}

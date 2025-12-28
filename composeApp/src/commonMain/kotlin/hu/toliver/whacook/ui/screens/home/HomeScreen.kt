package hu.toliver.whacook.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.components.EditableList
import hu.toliver.whacook.ui.components.MessageSnackbar
import hu.toliver.whacook.ui.components.buttons.PButton
import hu.toliver.whacook.ui.components.typography.Header
import hu.toliver.whacook.ui.screens.recipe.RecipeScreen
import kotlinx.coroutines.launch

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeScreenViewModel>()
        HomeScreenContent(
            state = viewModel.state,
            viewModel = viewModel
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    viewModel: HomeScreenViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Header()
        Spacer(Modifier.height(16.dp))
        PButton("Add Recipe")
    }
}

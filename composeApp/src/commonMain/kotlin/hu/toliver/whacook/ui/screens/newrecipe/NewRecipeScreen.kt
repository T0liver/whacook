package hu.toliver.whacook.ui.screens.newrecipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class NewRecipeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<NewRecipeScreenViewModel>()
        NewRecipeScreenContent(
            state = viewModel.state,
            viewModel = viewModel
        )
    }
}

@Composable
private fun NewRecipeScreenContent(
    @Suppress("UNUSED_PARAMETER")
    state: NewRecipeState,
    @Suppress("UNUSED_PARAMETER")
    viewModel: NewRecipeScreenViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("New Recipe Screen")
    }
}

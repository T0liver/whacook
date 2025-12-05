package hu.toliver.whacook.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
    val navigator = LocalNavigator.currentOrThrow
    val coroutineScope = rememberCoroutineScope()
    val ingredients = remember { mutableStateListOf<String>() }

    val showError = remember { mutableStateOf(false) }

    val meaningfulIngredients = ingredients.map { it.trim() }.filter { it.isNotEmpty() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header()

        Text(
            "Type your ingredients:",
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        EditableList(ingredients, buttonText = "Add ingredient", placeholderText = "ingredient")
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    showError.value = false
                    val generatedRecipe = viewModel.generateRecipe(meaningfulIngredients)
                    if (generatedRecipe != null) {
                        navigator.push(RecipeScreen(generatedRecipe))
                    } else {
                        showError.value = true
                    }
                }
            },
            enabled = meaningfulIngredients.isNotEmpty()
        ) {
            Text("Generate recipe")
        }

        MessageSnackbar(
            isLoading = state.isLoading,
            errorMessage = state.error,
        )

        if (showError.value) {
            MessageSnackbar(
                isLoading = false,
                errorMessage = "Error"
            )
        }

        if (meaningfulIngredients.isEmpty()) {
            Text(
                "Please add at least one ingredient",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

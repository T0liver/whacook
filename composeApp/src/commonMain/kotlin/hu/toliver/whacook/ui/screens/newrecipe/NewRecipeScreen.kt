package hu.toliver.whacook.ui.screens.newrecipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.components.*
import hu.toliver.whacook.ui.screens.recipe.RecipeScreen
import kotlinx.coroutines.launch

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
    state: NewRecipeState,
    viewModel: NewRecipeScreenViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    val ingredients = remember { mutableStateListOf<String>() }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val screenHeight = maxHeight
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .responsiveWidth(800.dp)
                    .heightIn(min = screenHeight),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Header("New Recipe")
                    Spacer(Modifier.height(16.dp))
                    EditableList(
                        ingredients,
                        "Type in what you have at home..."
                    )
                    PButton(
                        text = "Generate Recipe",
                        enabled = !state.isLoading,
                        onClick = {
                            scope.launch {
                                val recipe = viewModel.generateRecipe(ingredients.toList())
                                if (recipe != null) {
                                    navigator.push(RecipeScreen(recipe))
                                }
                            }
                        }
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BodyTextSmall("Type in one by one what ingredients you have at home and then a LLM will give you a recipe advice based on the list you give what to cook with units and preparation steps.")
                    Spacer(Modifier.height(120.dp))
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 130.dp)
        ) {
            MessageSnackbar(
                isLoading = state.isLoading,
                errorMessage = state.error,
                durationMs = 6000L,
                onShown = { viewModel.clearError() }
            )
        }
    }
}

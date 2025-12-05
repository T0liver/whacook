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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.EditableList
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

    val samleRecipe = remember {
        Recipe(
            id = "sample_id",
            name = "Sample Recipe",
            timeToMake = Duration(length = 20.0, unit = "minutes"),
            ingredients = mutableListOf(
                Ingredient(name = "Ingredient 1", unit = "grams", amount = 100.0),
                Ingredient(name = "Ingredient 2", unit = "pieces", amount = 2.0)
            ),
            steps = mutableListOf("Step 1", "Step 2"),
            tools = mutableListOf("Tool 1", "Tool 2"),
            serving = "Serves 2",
            generationTime = "Generated in 5 seconds",
            rating = 0
        )
    }
    // The editable list inserts an empty string when no items exist, so `ingredients.isEmpty()`
    // can be false even when the user did not enter any meaningful values. Build a filtered
    // list that trims whitespace and removes empty entries to check for true emptiness.
    val meaningfulIngredients = ingredients.map { it.trim() }.filter { it.isNotEmpty() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header()

        // Show loading / error from view model state so the parameter is used and the user
        // receives immediate feedback about generation progress/errors.
        if (state.isLoading) {
            Text(
                "Loading...",
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }
        state.error?.let { err ->
            Text(
                "Error: $err",
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }

        Text(
            "Type your ingredients:",
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        EditableList(ingredients, buttonText = "Add ingredient", placeholderText = "ingredient")
        Spacer(modifier = Modifier.height(40.dp))

        // Disable the button when there are no meaningful ingredients and show a hint
        Button(
            onClick = {
                coroutineScope.launch {
                    // Pass only non-blank, trimmed ingredients to generation
                    val generatedRecipe = viewModel.generateRecipe(meaningfulIngredients)
                    if (generatedRecipe != null) {
                        navigator.push(RecipeScreen(generatedRecipe))
                    } else {
                        // ViewModel state contains error if needed
                    }
                }
            },
            enabled = meaningfulIngredients.isNotEmpty()
        ) {
            Text("Generate recipe")
        }

        if (meaningfulIngredients.isEmpty()) {
            Text(
                "Please add at least one ingredient",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

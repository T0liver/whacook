package hu.toliver.whacook.ui.screens

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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.data.di.createRecipeGenerationUseCase
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import hu.toliver.whacook.ui.components.EditableList
import hu.toliver.whacook.ui.components.typography.Header
import kotlinx.coroutines.launch

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val recipeUseCase = remember { RecepieUseCase() }
        val recepieGenerationUseCase = remember { createRecipeGenerationUseCase() }
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
            Button(onClick = {
                coroutineScope.launch {
                    /*val generatedText = recepieGenerationUseCase.generateRecipe(ingredients)
                    val recipe = recipeUseCase.load(generatedText)*/
                    navigator.push(RecipeScreenHolder(samleRecipe))
                }
            }) {
                Text("Generate recipe")
            }
        }
    }
}

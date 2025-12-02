package hu.toliver.whacook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.toliver.whacook.data.di.createRecipeGenerationUseCase
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.ui.components.EditableList
import hu.toliver.whacook.ui.components.Header
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val ingredients = mutableStateListOf<String>()
    val recipe = Recipe

    var generatedText by remember { mutableStateOf<String?>(null) }

    val recipeUseCase = remember { createRecipeGenerationUseCase() }

    var showContent by remember { mutableStateOf(false) }

    MaterialTheme {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header()
            Text("Type your ingredients:",
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            EditableList(ingredients, buttonText = "Add ingredient", placeholderText = "ingredient")
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                showContent = !showContent
            }) {
                Text("Generate recipe" )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                LaunchedEffect(showContent) {
                    if (showContent) {
                        generatedText = try {
                            recipeUseCase.generateRecipe(ingredients).trimIndent()
                        } catch (e: Exception) {
                            "Error: ${e.message}"
                        }
                    }
                }
                generatedText?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
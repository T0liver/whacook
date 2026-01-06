package hu.toliver.whacook.ui.screens.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.BackButton
import hu.toliver.whacook.ui.components.Subheader
import hu.toliver.whacook.ui.components.BodyText
import hu.toliver.whacook.ui.components.BodyTextSmall
import hu.toliver.whacook.ui.components.Header

class RecipeScreen(
    val recipe: Recipe
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = RecipeScreenViewModel(recipe)
        RecipeScreenContent(recipe)
    }

}

@Composable
fun RecipeScreenContent(
    recipe: Recipe,
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        BackButton()
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 30.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Header(recipe.name)

            Subheader("Time to make")
            BodyText(recipe.timeToMake.toString())

            Subheader("Ingredients")
            for (ingredient in recipe.ingredients) {
                BodyText("- $ingredient")
            }

            Subheader("Steps")
            for ((index, step) in recipe.steps.withIndex()) {
                BodyText("${index + 1}. $step")
            }

            Subheader("Tools")
            for (tool in recipe.tools) {
                BodyText("- $tool")
            }

            Subheader("Serving")
            BodyText(recipe.serving)

            Spacer(Modifier.height(20.dp))
            BodyTextSmall(recipe.generationTime)
            
            Spacer(Modifier.height(100.dp))
        }
    }
}
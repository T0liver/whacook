package hu.toliver.whacook.ui.screens.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.*

class RecipeScreen(
    val recipe: Recipe
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = remember { RecipeScreenViewModel(recipe) }
        RecipeScreenContent(
            recipe = recipe,
            rating = viewModel.rating,
            onRatingChanged = viewModel::onRatingChanged
        )
    }

}

@Composable
fun RecipeScreenContent(
    recipe: Recipe,
    rating: Int,
    onRatingChanged: (Int) -> Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 30.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton()
                Spacer(modifier = Modifier.width(8.dp))
                Header(recipe.name)
            }

            RatingStars(
                rating = rating,
                onRatingChanged = onRatingChanged
            )

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
            
            Spacer(Modifier.height(120.dp))
        }
    }
}
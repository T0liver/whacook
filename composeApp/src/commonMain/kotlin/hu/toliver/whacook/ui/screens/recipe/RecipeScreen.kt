package hu.toliver.whacook.ui.screens.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.*
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.trashcan

class RecipeScreen(
    val recipe: Recipe
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<RecipeScreenViewModel> { parametersOf(recipe) }
        val state by viewModel.uiState.collectAsState()
        RecipeScreenContent(
            recipe = state,
            onRatingChanged = viewModel::onRatingChanged,
            toggleFavourite = viewModel::toggleFavourite
        )
    }

}

@Composable
fun RecipeScreenContent(
    recipe: Recipe,
    onRatingChanged: (Int) -> Unit,
    toggleFavourite: () -> Unit
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
            
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                FlowRow (
                    modifier = Modifier.responsiveWidth(600.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    RatingStars(
                        rating = recipe.rating,
                        onRatingChanged = onRatingChanged,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    FavouriteButton(
                        isFavourite = recipe.favourite,
                        onClick = toggleFavourite,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(
                        onClick = { /* TODO: Show delete popup */ },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(painterResource(Res.drawable.trashcan), "delete")
                    }
                }
            }

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
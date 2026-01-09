package hu.toliver.whacook.ui.screens.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.*
import hu.toliver.whacook.ui.screens.edit.EditScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.edit
import whacook.composeapp.generated.resources.trashcan

class RecipeScreen(
    val recipe: Recipe
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<RecipeScreenViewModel> { parametersOf(recipe) }
        val state by viewModel.uiState.collectAsState()
        RecipeScreenContent(
            recipe = state,
            onRatingChanged = viewModel::onRatingChanged,
            toggleFavourite = viewModel::toggleFavourite,
            onDelete = { navigator.pop() },
            onEdit = { navigator.push(EditScreen(state)) }
        )
    }

}

@Composable
fun RecipeScreenContent(
    recipe: Recipe,
    onRatingChanged: (Int) -> Unit,
    toggleFavourite: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    Box (
        modifier = Modifier.fillMaxSize()
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
                        onClick = onEdit,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(painterResource(Res.drawable.edit), "edit")
                    }
                    IconButton(
                        onClick = { showDeleteDialog = true },
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
        
        if (showDeleteDialog) {
            PopUpOverlay(
                headerText = "Delete Recipe",
                bodyText = "Are you sure you want to delete this recipe? This action cannot be undone.",
                dismiss = true,
                buttonText = "Yes",
                dismissText = "No",
                onConfirm = {
                    showDeleteDialog = false
                    onDelete()
                },
                onDismiss = { showDeleteDialog = false },
            )
        }
    }
}
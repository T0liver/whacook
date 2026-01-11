package hu.toliver.whacook.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.components.*
import hu.toliver.whacook.ui.screens.apikey.APIKeyScreen
import hu.toliver.whacook.ui.screens.newrecipe.NewRecipeScreen
import hu.toliver.whacook.ui.screens.recipe.RecipeScreen

class HomeScreen : Screen {
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
    @Suppress("UNUSED_PARAMETER")
    state: HomeState,
    @Suppress("UNUSED_PARAMETER")
    viewModel: HomeScreenViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    var showPopup by remember { mutableStateOf(state.showPopUp) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Header()
            Spacer(Modifier.height(20.dp))
            SearchCard(onSearchClick = {
                navigator.push(NewRecipeScreen())
            })
            Spacer(Modifier.height(16.dp))
            Subheader("Recent recipes")
            Spacer(Modifier.height(8.dp))
            Column(Modifier.fillMaxWidth(0.85f)) {
                if (state.recipes.isEmpty()) {
                    Box(Modifier
                        .fillMaxWidth(0.85f)
                        .padding(30.dp)
                        .align(Alignment.CenterHorizontally)
                    ) {
                        BodyText("No recipes found. Create a new recipe to get started!")
                        return@Column
                    }
                }
                state.recipes.forEach { recipe ->
                    RecipeCard(
                        title = recipe.name,
                        time = "${recipe.timeToMake.length.toInt()} ${recipe.timeToMake.unit}",
                        ingredientsCount = recipe.ingredients.size,
                        date = recipe.generationTime,
                    ) {
                        navigator.push(RecipeScreen(recipe))
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        if (showPopup) {
            PopUpOverlay(
                headerText = "Welcome to WhaCook!",
                bodyText = "I’m happy that you are here!\n\nIn order to use this application you have to get an API key as this app relies on a LLM and I don’t have enough money to provide if for free!",
                buttonText = "Take me there!",
                onDismiss = {
                    showPopup = false
                    state.showPopUp = false
                    navigator.push(APIKeyScreen())
                }
            )
        }
    }
}

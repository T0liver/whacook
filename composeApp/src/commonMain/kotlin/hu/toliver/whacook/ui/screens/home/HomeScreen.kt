package hu.toliver.whacook.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
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

            LazyColumn(
                modifier = Modifier.fillMaxWidth(0.85f).fillMaxHeight(),
                contentPadding = PaddingValues(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.recipes) { recipe ->
                    RecipeCard(
                        title = recipe.name,
                        time = recipe.timeToMake.toString(),
                        ingredientsCount = recipe.ingredients.size,
                        date = recipe.generationTime,
                    ) {
                        navigator.push(RecipeScreen(recipe))
                    }
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

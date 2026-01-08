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
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.*
import hu.toliver.whacook.ui.screens.apikey.APIKeyScreen
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
            SearchCard(onSearchClick = {showPopup = true})
            Spacer(Modifier.height(16.dp))
            Subheader("Recent recipes")
            Spacer(Modifier.height(8.dp))
            Column(Modifier.fillMaxWidth(0.85f)) {
                RecipeCard(
                    title = "Chocolate Lava Cake",
                    time = "30m",
                    ingredientsCount = 6,
                    date = "2025.11.12. 12:50",
                ) {
                    navigator.push(RecipeScreen(Recipe(
                        name = "Chocolate Lava Cake",
                        timeToMake = Duration(30.0, "minutes"),
                        ingredients = mutableListOf(
                            Ingredient("Dark chocolate", "g", 200.0),
                            Ingredient("Butter", "g", 100.0),
                            Ingredient("Sugar", "g", 150.0),
                            Ingredient("Eggs", "pcs", 3.0),
                            Ingredient("Flour", "g", 50.0),
                            Ingredient("Vanilla extract", "tsp", 1.0)
                        ),
                        steps = mutableListOf(
                            "Preheat oven to 220°C (425°F).",
                            "Melt chocolate and butter together.",
                            "In a separate bowl, whisk eggs and sugar until light and fluffy.",
                            "Combine melted chocolate mixture with egg mixture.",
                            "Fold in flour and vanilla extract.",
                            "Pour batter into greased ramekins and bake for 12-14 minutes."
                        ),
                        id = "cake1",
                        tools = mutableListOf("Oven", "Ramekins", "Mixing bowls", "Whisk"),
                        serving = "Serves 4",
                        favourite = false,
                        category = "Dessert",
                        generationTime = "Generated on 2025.11.10. 10:00",
                        rating = 3
                    )))
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

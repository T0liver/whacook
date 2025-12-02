package hu.toliver.whacook.ui.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import hu.toliver.whacook.domain.model.Recipe

data class RecipeScreenHolder(val recipe: Recipe) : Screen {
    @Composable
    override fun Content() {
        RecipeScreen(recipe)
    }
}

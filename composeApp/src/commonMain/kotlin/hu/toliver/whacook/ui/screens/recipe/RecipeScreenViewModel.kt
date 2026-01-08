package hu.toliver.whacook.ui.screens.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import hu.toliver.whacook.domain.model.Recipe

class RecipeScreenViewModel (
    private val recipe: Recipe
) : ScreenModel {
    var recipeData: Recipe = recipe
        private set

    var rating by mutableStateOf(0)
        private set

    fun onRatingChanged(newRating: Int) {
        rating = newRating
    }
}
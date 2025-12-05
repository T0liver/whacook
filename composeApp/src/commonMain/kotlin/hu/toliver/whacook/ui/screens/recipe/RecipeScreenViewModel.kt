package hu.toliver.whacook.ui.screens.recipe

import cafe.adriel.voyager.core.model.ScreenModel
import hu.toliver.whacook.domain.model.Recipe

class RecipeScreenViewModel (
    private val recipe: Recipe
) : ScreenModel {
    var recipeData: Recipe = recipe
        private set
}
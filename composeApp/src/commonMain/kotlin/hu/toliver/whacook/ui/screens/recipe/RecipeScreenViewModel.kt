package hu.toliver.whacook.ui.screens.recipe

import cafe.adriel.voyager.core.model.ScreenModel
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeScreenViewModel (
    recipe: Recipe,
    private val recipeUseCase: RecepieUseCase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow(recipe)
    val uiState = _uiState.asStateFlow()

    fun onRatingChanged(newRating: Int) {
        _uiState.update { currentRecipe ->
            val newRecipe = currentRecipe.copy()
            recipeUseCase.rate(newRecipe, newRating)
            newRecipe
        }
    }
    
    fun toggleFavourite() {
        _uiState.update { currentRecipe ->
            val newRecipe = currentRecipe.copy()
            val newFav = !newRecipe.favourite
            recipeUseCase.refavour(newRecipe, newFav)
            newRecipe
        }
    }
}
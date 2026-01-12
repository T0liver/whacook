package hu.toliver.whacook.ui.screens.recipe

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeScreenViewModel (
    val recipe: Recipe,
    private val recipeUseCase: RecepieUseCase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow(recipe)
    val uiState = _uiState.asStateFlow()

    fun onRatingChanged(newRating: Int) {
        _uiState.update { it.copy(rating = newRating) }
    }
    
    fun toggleFavourite() {
        _uiState.update { it.copy(favourite = !it.favourite) }
    }

    fun saveRecipe() {
        val state = _uiState.value
        recipe.rating = state.rating
        recipe.favourite = state.favourite
        screenModelScope.launch {
            recipeUseCase.saveToDatabase(recipe)
        }
    }

    fun deleteRecipe() {
        screenModelScope.launch {
            recipeUseCase.deleteFromDatabase(uiState.value)
        }
    }
}
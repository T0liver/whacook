package hu.toliver.whacook.ui.screens.recipe

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.repository.IRecipeRepository
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeScreenViewModel (
    recipe: Recipe,
    private val recipeUseCase: RecepieUseCase,
    private val recipeRepository: IRecipeRepository
) : ScreenModel {
    
    private val _uiState = MutableStateFlow(recipe)
    val uiState = _uiState.asStateFlow()

    fun onRatingChanged(newRating: Int) {
        _uiState.update { currentRecipe ->
            val newRecipe = currentRecipe.copy()
            recipeUseCase.rate(newRecipe, newRating)
            screenModelScope.launch {
                recipeRepository.saveRecipe(newRecipe)
            }
            newRecipe
        }
    }
    
    fun toggleFavourite() {
        _uiState.update { currentRecipe ->
            val newRecipe = currentRecipe.copy()
            val newFav = !newRecipe.favourite
            recipeUseCase.refavour(newRecipe, newFav)
            screenModelScope.launch {
                recipeRepository.saveRecipe(newRecipe)
            }
            newRecipe
        }
    }

    fun deleteRecipe() {
        screenModelScope.launch {
            recipeRepository.deleteRecipe(uiState.value.id)
        }
    }
}
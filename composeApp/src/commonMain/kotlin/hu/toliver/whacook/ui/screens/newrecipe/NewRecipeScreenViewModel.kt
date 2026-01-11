package hu.toliver.whacook.ui.screens.newrecipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase

class NewRecipeScreenViewModel(
    private val recepieGenerationUseCase: RecepieGenerationUseCase,
    private val recipeUseCase: RecepieUseCase
) : ScreenModel {
    var state by mutableStateOf(NewRecipeState())
        private set

    suspend fun generateRecipe(ingredients: List<String>): Recipe? {
        val validIngredients = ingredients.filter { it.isNotBlank() }
        if (validIngredients.isEmpty()) {
            state = state.copy(error = "Please add at least one ingredient.")
            return null
        }

        state = state.copy(isLoading = true, error = null)
        try {
            val json = recepieGenerationUseCase.generateRecipe(validIngredients)
            val recipe = recipeUseCase.load(json)
            state = state.copy(isLoading = false, error = null)
            return recipe
        } catch (e: Exception) {
            state = state.copy(isLoading = false, error = e.message ?: "Unknown error")
            return null
        }
    }

    fun clearError() {
        state = state.copy(error = null)
    }
}

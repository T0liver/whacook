package hu.toliver.whacook.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import kotlinx.coroutines.launch

class HomeScreenViewModel (
    private val recipeUseCase: RecepieUseCase,
    private val recipeGenerationUseCase: RecepieGenerationUseCase
) : ScreenModel {
    var state by mutableStateOf(HomeState.getDefaultState())
        private set

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        screenModelScope.launch {
            recipeUseCase.getAllFromDatabase().collect { recipes ->
                state = state.copy(recipes = recipes)
            }
        }
    }

    /**
     * Generate a recipe from the given ingredient list using the injected
     * generation use case, then parse it into a [Recipe] using [RecepieUseCase].
     *
     * On error the view model state is updated with the error message and null
     * is returned.
     */
    suspend fun generateRecipe(ingredients: List<String>): Recipe? {
        try {
            state = state.copy(isLoading = true, error = null)
            val json = recipeGenerationUseCase.generateRecipe(ingredients)
            val recipe = recipeUseCase.load(json)
            state = state.copy(isLoading = false, error = null)
            return recipe
        } catch (e: Exception) {
            state = state.copy(isLoading = false, error = e.message ?: "Unknown error")
            return null
        }
    }
}
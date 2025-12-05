package hu.toliver.whacook.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import hu.toliver.whacook.domain.model.Recipe

class HomeScreenViewModel (
    private val recipeUseCase: RecepieUseCase,
    private val recepieGenerationUseCase: RecepieGenerationUseCase
) : ScreenModel {
    var state by mutableStateOf(HomeState(
        isLoading = true,
        error = null
    ))
        private set

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
            val json = recepieGenerationUseCase.generateRecipe(ingredients)
            val recipe = recipeUseCase.load(json)
            state = state.copy(isLoading = false, error = null)
            return recipe
        } catch (e: Exception) {
            state = state.copy(isLoading = false, error = e.message ?: "Unknown error")
            return null
        }
    }
}
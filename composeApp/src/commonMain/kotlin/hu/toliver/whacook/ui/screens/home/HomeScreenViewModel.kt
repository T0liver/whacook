package hu.toliver.whacook.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.domain.usecase.RecipeUseCase
import kotlinx.coroutines.launch

class HomeScreenViewModel (
    private val recipeUseCase: RecipeUseCase,
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
}
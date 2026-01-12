package hu.toliver.whacook.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.data.local.PopUpManager
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecipeUseCase
import kotlinx.coroutines.launch

class HomeScreenViewModel (
    private val recipeUseCase: RecipeUseCase,
    private val popUpManager: PopUpManager
) : ScreenModel {
    var state by mutableStateOf(HomeState.getDefaultState())
        private set

    private var allRecipes: List<Recipe> = emptyList()

    init {
        loadRecipes()
        checkWelcomeStatus()
    }

    private fun loadRecipes() {
        screenModelScope.launch {
            recipeUseCase.getAllFromDatabase().collect { recipes ->
                allRecipes = recipes
                updateState()
            }
        }
    }

    fun sortList(sortType: SortType) {
        state = state.copy(sortType = sortType)
        updateState()
    }

    fun toggleFavourite() {
        state = state.copy(isFavouriteOnly = !state.isFavouriteOnly)
        updateState()
    }

    private fun updateState() {
        var result = allRecipes
        if (state.isFavouriteOnly) {
            result = result.filter { it.favourite }
        }
        result = when(state.sortType) {
            SortType.DATE -> result.reversed()
            SortType.RATING -> result.sortedByDescending { it.rating }
        }

        state = state.copy(recipes = result)
    }

    private fun checkWelcomeStatus() {
        screenModelScope.launch {
            if (popUpManager.isWelcomeShown()) {
                state = state.copy(showPopUp = false)
            }
        }
    }

    fun dismissPopUp() {
        screenModelScope.launch {
            popUpManager.setWelcomeShown()
            state = state.copy(showPopUp = false)
        }
    }
}
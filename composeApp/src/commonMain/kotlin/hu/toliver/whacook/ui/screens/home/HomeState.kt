package hu.toliver.whacook.ui.screens.home

import hu.toliver.whacook.domain.model.Recipe

data class HomeState (
    val isLoading: Boolean,
    val error: String?,
    var showPopUp: Boolean,
    val recipes: List<Recipe>
) {
    companion object {
        fun getDefaultState(): HomeState {
            return HomeState(
                isLoading = false,
                error = null,
                showPopUp = false,
                recipes = emptyList()
            )
        }
    }
}
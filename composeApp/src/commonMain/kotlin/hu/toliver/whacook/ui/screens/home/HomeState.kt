package hu.toliver.whacook.ui.screens.home

import hu.toliver.whacook.domain.model.Recipe

enum class SortType {
    DATE,
    RATING
}

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

data class HomeState (
    val isLoading: Boolean,
    val error: String?,
    var showPopUp: Boolean,
    val recipes: List<Recipe>,
    val sortType: SortType,
    val sortOrder: SortOrder,
    val isFavouriteOnly: Boolean
) {
    companion object {
        fun getDefaultState(): HomeState {
            return HomeState(
                isLoading = false,
                error = null,
                showPopUp = true,
                recipes = emptyList(),
                sortType = SortType.DATE,
                sortOrder = SortOrder.DESCENDING,
                isFavouriteOnly = false
            )
        }
    }
}
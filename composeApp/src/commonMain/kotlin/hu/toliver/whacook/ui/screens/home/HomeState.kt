package hu.toliver.whacook.ui.screens.home

data class HomeState (
    val isLoading: Boolean,
    val error: String?,
    var showPopUp: Boolean
) {
    companion object {
        fun getDefaultState(): HomeState {
            return HomeState(
                isLoading = true,
                error = null,
                showPopUp = true
            )
        }
    }
}
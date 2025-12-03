package hu.toliver.whacook.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class HomeScreenViewModel : ScreenModel {
    var state by mutableStateOf(HomeState())
        private set

    init {
        // ...
    }
}

data class HomeState(
    val welcomeMessage: String = "_empty_"
)
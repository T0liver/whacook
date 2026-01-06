package hu.toliver.whacook.ui.screens.newrecipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class NewRecipeScreenViewModel : ScreenModel {
    var state by mutableStateOf(NewRecipeState())
        private set
}

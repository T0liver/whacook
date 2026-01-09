package hu.toliver.whacook.ui.screens.recipepreferences

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class RecipePreferencesScreenViewModel : ScreenModel {
    
    var state by mutableStateOf(RecipePreferencesState())
        private set
    
    fun updatePreference(value: String) {
        state = state.copy(preference = value)
    }
    
    fun savePreference() {
        // TODO: Persist the preference
        // For now, we simulate a successful save for the demo
        println("Saving Preference: ${state.preference}")
        state = state.copy(isSaved = true)
    }
}
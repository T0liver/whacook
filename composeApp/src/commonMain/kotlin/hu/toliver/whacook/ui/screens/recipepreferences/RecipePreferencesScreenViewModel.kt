package hu.toliver.whacook.ui.screens.recipepreferences

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.data.local.RecipePreferencesManager
import kotlinx.coroutines.launch

class RecipePreferencesScreenViewModel(
    private val preferencesManager: RecipePreferencesManager
) : ScreenModel {

    var state by mutableStateOf(RecipePreferencesState())
        private set

    init {
        screenModelScope.launch {
            val savedPreferences = preferencesManager.getPreferences()
            if (!savedPreferences.isNullOrEmpty()) {
                state = state.copy(preference = savedPreferences)
            }
        }
    }

    fun updatePreference(value: String) {
        state = state.copy(preference = value)
    }
    
    fun savePreference() {
        screenModelScope.launch {
            preferencesManager.savePreferences(state.preference)
            state = state.copy(isSaved = true)
        }
    }
}
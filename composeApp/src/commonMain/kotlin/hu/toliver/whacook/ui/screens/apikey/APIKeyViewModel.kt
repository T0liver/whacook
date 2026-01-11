package hu.toliver.whacook.ui.screens.apikey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.data.local.APIKeyManager
import kotlinx.coroutines.launch

class APIKeyViewModel(
    private val preferencesManager: APIKeyManager
) : ScreenModel {

    var state by mutableStateOf(APIKeyState())
        private set

    init {
        val savedKey = preferencesManager.apiKey
        if (!savedKey.isNullOrEmpty()) {
            state = state.copy(apiKey = savedKey)
        }
    }

    fun updateApiKey(value: String) {
        state = state.copy(apiKey = value)
    }

    fun saveApiKey() {
        screenModelScope.launch {
            preferencesManager.saveApiKey(state.apiKey)
            state = state.copy(isSaved = true)
        }
    }
}

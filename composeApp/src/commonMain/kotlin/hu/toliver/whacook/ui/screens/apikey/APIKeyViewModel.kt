package hu.toliver.whacook.ui.screens.apikey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class APIKeyViewModel : ScreenModel {

    var state by mutableStateOf(APIKeyState())
        private set

    fun updateApiKey(value: String) {
        state = state.copy(apiKey = value)
    }

    fun saveApiKey() {
        // TODO: Persist the API key securely
        // For now, we simulate a successful save for the demo
        println("Saving API Key: ${state.apiKey}")
        state = state.copy(isSaved = true)
    }
}


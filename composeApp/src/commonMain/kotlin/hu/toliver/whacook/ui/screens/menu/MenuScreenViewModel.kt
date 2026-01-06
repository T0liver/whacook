package hu.toliver.whacook.ui.screens.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class MenuScreenViewModel : ScreenModel {
    var state by mutableStateOf(MenuState())
        private set

    // Placeholder for future logic (e.g., logout, fetch user info)
    fun onLogout() {
        // Implement logout logic
    }
}


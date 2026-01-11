package hu.toliver.whacook.data.local

import com.russhwolf.settings.Settings

class PreferencesManager(private val settings: Settings) {
    
    companion object {
        const val GEMINI_API_KEY = "gemini_api_key"
    }

    val apiKey: String?
        get() = settings.getStringOrNull(GEMINI_API_KEY)

    fun saveApiKey(apiKey: String) {
        settings.putString(GEMINI_API_KEY, apiKey)
    }
}

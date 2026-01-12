package hu.toliver.whacook.data.local

import hu.toliver.whacook.data.local.entity.SettingEntity
import hu.toliver.whacook.domain.repository.DatabaseRepository

class APIKeyManager(private val repository: DatabaseRepository) {

    companion object {
        const val GEMINI_API_KEY = "gemini_api_key"
    }

    suspend fun getApiKey(): String? = repository.getSettingByKey(GEMINI_API_KEY)?.value

    suspend fun saveApiKey(apiKey: String) {
        repository.insertSetting(SettingEntity(GEMINI_API_KEY, apiKey))
    }
}

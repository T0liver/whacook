package hu.toliver.whacook.data.local

import hu.toliver.whacook.data.local.dao.SettingDao
import hu.toliver.whacook.data.local.entity.SettingEntity

class APIKeyManager(private val settingDao: SettingDao) {

    companion object {
        const val GEMINI_API_KEY = "gemini_api_key"
    }

    suspend fun getApiKey(): String? = settingDao.getSetting(GEMINI_API_KEY)

    suspend fun saveApiKey(apiKey: String) {
        settingDao.insertSetting(SettingEntity(GEMINI_API_KEY, apiKey))
    }
}

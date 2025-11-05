package com.cs407.uteri.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cs407.uteri.ui.screen.ProfileSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ProfileSettings (
    val passwordEnabled: Boolean = false,
    val offlineMode: Boolean = false
)

class ProfileSettingsStorage(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("profile_settings_storage")

        private object PreferenceKeys {
            val PASSWORD_ENABLED = booleanPreferencesKey("passwordEnabled")
            val OFFLINE_MODE = booleanPreferencesKey("offlineMode")
        }
    }

    val profileSettingsFlow: Flow<ProfileSettings> = context.dataStore.data.map { settings ->
        val passwordEnabled = settings[PreferenceKeys.PASSWORD_ENABLED] ?: false
        val offlineMode = settings[PreferenceKeys.OFFLINE_MODE] ?: false

        ProfileSettings(passwordEnabled, offlineMode)
    }

    suspend fun savePasswordSetting(passwordEnabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.PASSWORD_ENABLED] = passwordEnabled
        }
    }
}
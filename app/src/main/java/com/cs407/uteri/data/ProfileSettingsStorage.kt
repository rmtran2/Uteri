package com.cs407.uteri.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class ProfileSettings (
    val passwordEnabled: Boolean = false,
    val offlineMode: Boolean = false
)

class ProfileSettingsStorage(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("profile_settings_storage")

        private object PreferenceKeys {
            val PASSWORD_ENABLED = booleanPreferencesKey("passwordEnabled")
            val OFFLINE_MODE = booleanPreferencesKey("offlineMode")
        }
    }

    val profileSettingsFlow: Flow<ProfileSettings> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { settings ->
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
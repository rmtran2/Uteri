package com.cs407.uteri.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cs407.uteri.data.ProfileSettings
import com.cs407.uteri.data.ProfileSettingsStorage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileSettingsViewModel(
    private val preferencesManager: ProfileSettingsStorage
) : ViewModel() {

    val profileSettings: StateFlow<ProfileSettings> = preferencesManager.profileSettingsFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileSettings()
    )
    fun togglePasswordProtected(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.savePasswordSetting(enabled)
        }
    }

    fun toggleOfflineData(offlineMode: Boolean) {
        viewModelScope.launch {
            preferencesManager.saveOfflineSetting(offlineMode)
        }
    }
}

class ProfileSettingsViewModelFactory(
    private val preferencesManager: ProfileSettingsStorage
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileSettingsViewModel(preferencesManager) as T
    }
}
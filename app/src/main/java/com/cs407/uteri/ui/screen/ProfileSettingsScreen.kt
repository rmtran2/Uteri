package com.cs407.uteri.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cs407.uteri.data.ProfileSettingsStorage

@Preview
@Composable
fun ProfileSettingsScreen() {
    Scaffold(
        bottomBar = { BottomNavBar() }
    ) {innerPadding ->
        Text(text = "Hi", modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileSettings(
    modifier: Modifier = Modifier
) {
    val store = ProfileSettingsStorage(LocalContext.current)
    val profileSettings = store.profileSettingsFlow.collectAsStateWithLifecycle(ProfileSettings())
    val scope = rememberCoroutineScope()

}
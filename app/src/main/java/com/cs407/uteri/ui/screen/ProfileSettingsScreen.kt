package com.cs407.uteri.ui.screen

import android.widget.ToggleButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cs407.uteri.data.ProfileSettingsStorage
import com.cs407.uteri.ui.utils.Navbar

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfileSettingsScreen() {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Profile Settings", fontWeight = FontWeight.Bold, fontSize = 36.sp) } ) },
        bottomBar = { Navbar() }
    ) {innerPadding ->
        ProfileSettings(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileSettings(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val store = ProfileSettingsStorage(context)
    val profileSettings = store.profileSettingsFlow.collectAsStateWithLifecycle(ProfileSettings())
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Spacer(Modifier.padding(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

            ) {
                Text(text = "Biometric/Password Enabled", fontWeight = FontWeight.Bold)
                Text(text = "Require authentication to enter this app")
            }
            Switch(
                checked = false,
                onCheckedChange = {  },
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

            ) {
                Text(text = "Online Mode", fontWeight = FontWeight.Bold)
                Text(text = "Store your data on the cloud for easy syncing")
            }
            Switch(
                checked = false,
                onCheckedChange = {  },
            )

        }
    }

}
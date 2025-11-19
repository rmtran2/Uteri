package com.cs407.uteri.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs407.uteri.R
import com.cs407.uteri.data.ProfileSettingsStorage
import com.cs407.uteri.ui.utils.Navbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    profileSettingsManager: ProfileSettingsStorage,
    onNavigateBack: () -> Unit,
    navController: NavController
) {

    val viewModel: ProfileSettingsViewModel = viewModel(
        factory = ProfileSettingsViewModelFactory(profileSettingsManager)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                Text(
                    "Profile Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            })
        },
        bottomBar = { Navbar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE0E0E0),
                        Color(0x2CE91E63)
                    ),
                    center = Offset(500f, 700f),
                    radius = 1000f,
                    tileMode = TileMode.Clamp
                )
            )
        ) {
            ProfileSettings1(viewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ProfileSettings1(
    viewModel: ProfileSettingsViewModel,
    modifier: Modifier = Modifier
) {
    val profileSettings by viewModel.profileSettings.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var pendingPasswordChange by remember { mutableStateOf(profileSettings.passwordEnabled) }

    Column(modifier = modifier) {
        Spacer(Modifier.padding(20.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(100.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

            ) {
                Text(text = "Biometric/Password Enabled", fontWeight = FontWeight.Bold)
                Text(text = "Require authentication to enter this app")
            }
            Switch(
                checked = profileSettings.passwordEnabled,
                onCheckedChange = {
                    if (profileSettings.passwordEnabled) {
                        viewModel.togglePasswordProtected(it)
                    } else {
                        pendingPasswordChange = it
                        showDialog = true
                    }
                },
            )
        }
        Text("User authentication can always be disabled, but users have this option in " +
                "order to add another layer of privacy on their health data.", modifier = Modifier.padding(8.dp))
        Spacer(Modifier.padding(16.dp))
        Image(
            painter = painterResource(id = R.drawable.cloud_54__1_),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

            ) {
                Text(text = "Online Mode", fontWeight = FontWeight.Bold)
                Text(text = "Store your data on the cloud for easy syncing")
            }
            Switch(
                checked = profileSettings.offlineMode,
                onCheckedChange = { viewModel.toggleOfflineData(it) },
            )

        }
        Text("As a user, you can either choose to store your data online to enable syncing between devices or to store it " +
                "on your device in order to increase privacy.", modifier = Modifier.padding(8.dp))

        Spacer(Modifier.padding(16.dp))

        if(showDialog && !profileSettings.passwordEnabled) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    pendingPasswordChange = false
                },
                title = { Text("Confirm Authentication Change") },
                text = { Text("Changing to password protected mode will send you to a login page. Please input the credentials " +
                            "you would like to use to access the app with. Password protected mode can be disabled at any time in the profile settings.",
                            modifier = Modifier.align(Alignment.CenterHorizontally))
                       },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            viewModel.togglePasswordProtected(pendingPasswordChange)
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            pendingPasswordChange = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        Spacer(Modifier.padding(16.dp))

        Image(
            painter = painterResource(id = R.drawable.new_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally)
        )
    }

}
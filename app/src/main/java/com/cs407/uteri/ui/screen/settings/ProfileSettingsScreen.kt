package com.cs407.uteri.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs407.uteri.R
import com.cs407.uteri.data.ProfileSettings
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
        ProfileSettings1(viewModel, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileSettings1(
    viewModel: ProfileSettingsViewModel,
    modifier: Modifier = Modifier
) {
    val profileSettings by viewModel.profileSettings.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                checked = profileSettings.passwordEnabled,
                onCheckedChange = { viewModel.togglePasswordProtected(it) },
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
                onCheckedChange = { viewModel.togglePasswordProtected(it) },
            )

        }

        Spacer(Modifier.padding(16.dp))

        ElevatedCard {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.padding(16.dp))

        Image(
            painter = painterResource(id = R.drawable.uteruslogo),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally)
        )
    }

}
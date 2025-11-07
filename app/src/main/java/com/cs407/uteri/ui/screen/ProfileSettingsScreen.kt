package com.cs407.uteri.ui.screen

import android.widget.ToggleButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.cs407.uteri.R
import com.cs407.uteri.data.ProfileSettingsStorage
import com.cs407.uteri.ui.utils.Navbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    onNavigateBack: () -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    "Profile Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            })
        },
        bottomBar = { Navbar(navController) }
    ) { innerPadding ->
        ProfileSettings1(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileSettings1(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val store = ProfileSettingsStorage(context)
    //val profileSettings = store.profileSettingsFlow.collectAsStateWithLifecycle(ProfileSettings())
    val scope = rememberCoroutineScope()

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

        ElevatedCard {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge
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
            modifier = Modifier.size(250.dp)
        )
    }

}
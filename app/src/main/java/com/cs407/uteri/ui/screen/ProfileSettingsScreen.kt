package com.cs407.uteri.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cs407.uteri.ui.utils.Navbar

@Composable
fun ProfileSetingsScreen(
    onNavigateBack: () -> Unit,
    navController: NavController
){
    Scaffold (
        bottomBar = {
            Navbar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {

        }
    }
}



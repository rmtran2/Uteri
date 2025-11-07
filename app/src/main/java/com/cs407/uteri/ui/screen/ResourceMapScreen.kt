package com.cs407.uteri.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cs407.uteri.ui.utils.Navbar
import com.google.maps.android.compose.GoogleMap

@Composable
fun ResourceMapScreen(
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
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}


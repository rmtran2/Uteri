package com.cs407.uteri

import com.cs407.uteri.ui.screen.TimerEndDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs407.uteri.data.ProfileSettingsStorage
import com.cs407.uteri.ui.screen.CalendarScreen
import com.cs407.uteri.ui.screen.HomePage
import com.cs407.uteri.ui.screen.LoginScreen
import com.cs407.uteri.ui.screen.settings.ProfileSettingsScreen
import com.cs407.uteri.ui.screen.ResourceMapScreen
import com.cs407.uteri.ui.screen.TimerScreen
import com.cs407.uteri.ui.screen.TimerViewModel
import com.cs407.uteri.ui.screen.settings.ProfileSettingsViewModel
import com.cs407.uteri.ui.screen.settings.ProfileSettingsViewModelFactory
import com.cs407.uteri.ui.theme.UteriTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefsManager = ProfileSettingsStorage(this)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            UteriTheme {
                // single TimerViewModel instance for the entire app
                val timerViewModel: TimerViewModel = viewModel()
                AppNavigation(prefsManager = prefsManager, timerViewModel = timerViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(
    prefsManager: ProfileSettingsStorage,
    timerViewModel: TimerViewModel
){
    val navController = rememberNavController()
    val viewModel: ProfileSettingsViewModel = viewModel(factory = ProfileSettingsViewModelFactory(prefsManager))
    val profileSettings by viewModel.profileSettings.collectAsState()
    val showTimerDialog by timerViewModel.showTimerEndDialog.collectAsState()

    val startDest = when (profileSettings.passwordEnabled) {
        null -> Screen.HOME.route
        true -> Screen.LOGIN.route
        false -> Screen.HOME.route
    }

    // Timer dialog visible globally
    if (showTimerDialog) {
        TimerEndDialog(onDismiss = { timerViewModel.dismissDialog() })
    }

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        composable(Screen.HOME.route) {
            HomePage(
                onNavigateToCalendar = {navController.navigate(Screen.CALENDAR.route)},
                onNavigateToMap = {navController.navigate(Screen.MAP.route)},
                onNavigateToTimer = {navController.navigate(Screen.TIMER.route)},
                onNavigateToProfile = {navController.navigate(Screen.PROFILE.route)},
            )
        }
        composable(Screen.CALENDAR.route) {
            CalendarScreen(navController)
        }
        composable(Screen.MAP.route) {
            ResourceMapScreen({ navController.navigate(Screen.HOME.route)}, navController)
        }
        composable(Screen.TIMER.route) {
            TimerScreen(
                onNavigateBack = { navController.navigate(Screen.HOME.route) },
                navController = navController,
                viewModel = timerViewModel
            )
        }
        composable(Screen.PROFILE.route) {
            ProfileSettingsScreen(prefsManager, { navController.navigate(Screen.HOME.route) }, navController)
        }
        composable(Screen.LOGIN.route) {
            LoginScreen(onNavigateToHome = { navController.navigate(Screen.HOME.route) })
        }
    }
}

enum class Screen(val route: String) {
    HOME("home"),
    CALENDAR("calendar"),
    TIMER("timer"),
    MAP("map"),
    PROFILE("profile"),
    LOGIN("login")
}

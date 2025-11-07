package com.cs407.uteri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs407.uteri.ui.screen.CalendarScreen
import com.cs407.uteri.ui.screen.HomePage
import com.cs407.uteri.ui.screen.ProfileSettingsScreen
import com.cs407.uteri.ui.screen.ResourceMapScreen
import com.cs407.uteri.ui.screen.TimerScreen
import com.cs407.uteri.ui.theme.UteriTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UteriTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HOME.route
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
            CalendarScreen ({navController.navigate("home")}, navController)
        }
        composable(Screen.MAP.route) {
            ResourceMapScreen ({ navController.navigate("home")}, navController)
        }
        composable(Screen.TIMER.route) {
            TimerScreen ({ navController.navigate("home") }, navController)
        }
        composable(Screen.PROFILE.route) {
            ProfileSettingsScreen ({ navController.navigate("home") }, navController)
        }

    }

}

enum class Screen(val route: String) {
    HOME("home"),
    CALENDAR("calendar"),
    TIMER("timer"),
    MAP("map"),
    PROFILE("profile")
}

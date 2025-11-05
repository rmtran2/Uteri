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
import com.cs407.uteri.ui.screen.ResourceMapScreen
import com.cs407.uteri.ui.screen.TimerScreen
import com.cs407.uteri.ui.theme.UteriTheme

// MainActivity is the entry point of the application
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
        startDestination = "home"
    ) {
        composable("home") {
            HomePage(
                onNavigateToCalendar = {navController.navigate("calendar_screen")},
                onNavigateToMap = {navController.navigate("map_screen")},
                onNavigateToTimer = {navController.navigate("timer_screen")},
            )
        }
        composable("calendar_screen") { CalendarScreen ({navController.navigate("home")}) }
        composable("map_screen") { ResourceMapScreen ({ navController.navigate("home") }) }
        composable("timer_screen") { TimerScreen ({ navController.navigate("home") }) }
    }

}

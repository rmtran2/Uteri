package com.cs407.uteri

import com.cs407.uteri.ui.screen.HomePage
import android.R.attr.text
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs407.uteri.ui.theme.UteriTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs407.uteri.ui.screen.CalendarScreen
import com.cs407.uteri.ui.screen.HomePage
import com.cs407.uteri.ui.screen.ResourceMapScreen
import com.cs407.uteri.ui.screen.TimerScreen
import org.intellij.lang.annotations.JdkConstants

// MainActivity is the entry point of the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables drawing behind system bars for a full-screen look
        enableEdgeToEdge()
        // Sets the UI content for this Activity using Jetpack Compose
        setContent {
            // Applies the app's theme
                // Calls the AppNavigation composable to set up navigation
                AppNavigation()
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

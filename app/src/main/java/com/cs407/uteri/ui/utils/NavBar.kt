package com.cs407.uteri.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cs407.uteri.R
import com.cs407.uteri.Screen

@Composable
fun Navbar(
    navController: NavController
) {
    var currentPage by remember { mutableStateOf(navController.currentDestination?.route) }
    Surface (
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(8.dp)
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavbarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (currentPage == Screen.TIMER.route) R.drawable.calendar_icon
                            else R.drawable.calendar_filled_24
                        ),
                        contentDescription = "Calendar"
                    )
                },
                label = "Calendar",
                selected = currentPage == Screen.CALENDAR.route,
                onClick = {
                    currentPage = Screen.CALENDAR.route
                    navController.navigate(Screen.CALENDAR.route)
                }
            )

            NavbarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (currentPage == Screen.TIMER.route) R.drawable.timer_filled_24px
                            else R.drawable.timer_outlined_24px
                        ),
                        contentDescription = "Timer"
                    )
                },
                label = "Timer",
                selected = currentPage == Screen.TIMER.route,
                onClick = {
                    currentPage = Screen.TIMER.route
                    navController.navigate(Screen.TIMER.route)
                }
            )

            NavbarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (currentPage == Screen.MAP.route) R.drawable.location_filled_24
                            else R.drawable.location_outlined_24
                        ),
                        contentDescription = "Map"
                    )
                },
                label = "Map",
                selected = currentPage == Screen.MAP.route,
                onClick = {
                    currentPage = Screen.MAP.route
                    navController.navigate(Screen.MAP.route)
                }
            )

            NavbarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (currentPage == Screen.PROFILE.route) R.drawable.person_filled_24
                            else R.drawable.person_outlined_24
                        ),
                        contentDescription = "Profile"
                    )
                },
                label = "Profile",
                selected = currentPage == Screen.PROFILE.route,
                onClick = {
                    currentPage = Screen.PROFILE.route
                    navController.navigate(Screen.PROFILE.route)
                }
            )
        }
    }
}

@Composable
fun NavbarItem(
    icon: @Composable () -> Unit,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
        ) {
            icon()
        }
        Text(
            text = label,
            fontSize = 12.sp
        )
    }
}
package com.cs407.uteri.ui.utils

import android.R.attr.label
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs407.uteri.R

enum class Page {
    HOME, TIMER, MAP
}

@Preview
@Composable
fun Navbar(

) {
    var currentPage by remember { mutableStateOf(Page.HOME) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavbarItem(
            icon = {
                Icon(
                    imageVector = if (currentPage == Page.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = "Home",
            selected = currentPage == Page.HOME,
            onClick = { currentPage = Page.HOME }
        )

        NavbarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (currentPage == Page.TIMER) R.drawable.timer_filled_24px
                        else R.drawable.timer_outlined_24px
                    ),
                    contentDescription = "Timer"
                )
            },
            label = "Timer",
            selected = currentPage == Page.TIMER,
            onClick = { currentPage = Page.TIMER }
        )

        NavbarItem(
            icon = {
                Icon(
                    imageVector = if (currentPage == Page.MAP) Icons.Outlined.Star else Icons.Filled.Star,
                    contentDescription = "Map"
                )
            },
            label = "Map",
            selected = currentPage == Page.MAP,
            onClick = { currentPage = Page.MAP }
        )
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
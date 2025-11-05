package com.cs407.uteri.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.cs407.uteri.R

@Preview
@Composable
fun BottomNavBar() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Button(onClick = {
                    //navigate to map
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.DarkGray,
                    containerColor = Color.LightGray
                ),

            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null
                )
                Text(text = stringResource(id = R.string.map))
            }
            Button(onClick = {
                    //navigate to calendar
                },
                modifier = Modifier.background(color = Color(0xffe91e63))
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Text(text = stringResource(id = R.string.calendar))
            }
            Button(onClick = {
                //navigate to timer
            },
                modifier = Modifier.background(color = Color(0xffe91e63))
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_timer_24),
                    contentDescription = null
                )
                Text(text = stringResource(id = R.string.timer))
            }
        }
    }
}
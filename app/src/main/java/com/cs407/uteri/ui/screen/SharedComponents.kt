package com.cs407.uteri.ui.screen

import android.media.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProfileButton(
    onNavigateToProfile: () -> Unit,
){
    IconButton(
        onClick = {
            onNavigateToProfile()
        }
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )
    }
}

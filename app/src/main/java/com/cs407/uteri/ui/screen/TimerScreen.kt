package com.cs407.uteri.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.cs407.uteri.R
import com.cs407.uteri.ui.utils.Navbar
import com.cs407.uteri.ui.theme.UterUsPink
import com.cs407.uteri.ui.theme.UterUsYellow

@Composable
fun TimerScreen(
    onNavigateBack: () -> Unit,
    navController: NavController
) {
    var hoursInput by remember { mutableStateOf("0") }
    var minutesInput by remember { mutableStateOf("0") }
    var remainingTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    var showTimerEndDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Show notification dialog when timer ends
    if (showTimerEndDialog) {
        TimerEndDialog(
            onDismiss = { showTimerEndDialog = false }
        )
    }

    // Function to start the timer
    fun startTimer(hours: Long, minutes: Long = 0) {
        remainingTime = hours * 3600 + minutes * 60
        isRunning = true
        showTimerEndDialog = false // Reset dialog when starting new timer

        scope.launch {
            while (remainingTime > 0 && isRunning) {
                delay(1000)
                remainingTime--
            }

            if (remainingTime == 0L && isRunning) {
                // Timer completed naturally (not stopped manually)
                isRunning = false
                showTimerEndDialog = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFae8), // Light peach
                        Color(0xFFFFE1EB), // Light pink
                        Color(0xFFFFFae8)  // Light peach
                    )
                )
            )
    ) {
        Scaffold(
            bottomBar = { Navbar(navController) },
            containerColor = Color.Transparent // Make scaffold transparent to show gradient
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Timer title
                Text(
                    text = stringResource(id = R.string.Timer),
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 90.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                // Main box
                Box(
                    modifier = Modifier
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    UterUsPink,
                                    UterUsYellow
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = R.string.enter_time_label),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth(),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        // Inputs row
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = hoursInput,
                                onValueChange = { if (it.all { c -> c.isDigit() }) hoursInput = it },
                                label = { Text("Hours") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .width(120.dp)
                                    .background(colorResource(id = R.color.light_pink), RoundedCornerShape(4.dp)),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.light_pink),
                                    unfocusedContainerColor = colorResource(id = R.color.light_pink),
                                    disabledContainerColor = colorResource(id = R.color.light_pink),
                                )
                            )

                            Spacer(modifier = Modifier.width(30.dp))

                            OutlinedTextField(
                                value = minutesInput,
                                onValueChange = { if (it.all { c -> c.isDigit() }) minutesInput = it },
                                label = { Text("Minutes") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .width(120.dp)
                                    .background(colorResource(id = R.color.light_pink), RoundedCornerShape(4.dp)),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.light_pink),
                                    unfocusedContainerColor = colorResource(id = R.color.light_pink),
                                    disabledContainerColor = colorResource(id = R.color.light_pink),
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                // Defaults row
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 32.dp)) {
                    Text(
                        text = "Defaults:",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        listOf(8L, 4L, 2L).forEach { hrs ->
                            Button(
                                onClick = { startTimer(hrs, 0) },
                                shape = CircleShape,
                                modifier = Modifier.size(75.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF69B4) // Pink color for buttons
                                )
                            ) {
                                Text("$hrs h", fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.width(22.dp))
                        }
                    }
                }

                // Countdown display
                val displayHours = remainingTime / 3600
                val displayMinutes = (remainingTime % 3600) / 60
                val displaySeconds = remainingTime % 60

                Text(
                    text = String.format("%02d:%02d:%02d", displayHours, displayMinutes, displaySeconds),
                    fontSize = 64.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Start/Stop button
                Button(
                    onClick = {
                        if (!isRunning) {
                            startTimer(
                                hoursInput.toLongOrNull() ?: 0,
                                minutesInput.toLongOrNull() ?: 0
                            )
                        } else {
                            isRunning = false
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .width(180.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6489) // Pink color
                    )
                ) {
                    Text(if (isRunning) "stop" else "start", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun TimerEndDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFFF6489)
                )
            ) {
                Text("OK", fontSize = 18.sp)
            }
        },
        title = {
            Text(
                text = "Timer Complete!",
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = "Your timer has finished.",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
        containerColor = Color(0xFFFFF0F5), // Light pink background
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 0.dp
    )
}

//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//    val navController = rememberNavController()
//    TimerScreen(
//        onNavigateBack = {},
//        navController = navController
//    )
//}
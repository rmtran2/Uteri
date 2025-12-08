package com.cs407.uteri.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs407.uteri.R
import com.cs407.uteri.ui.utils.Navbar
import com.cs407.uteri.ui.theme.UterUsPink
import com.cs407.uteri.ui.theme.UterUsYellow
import com.cs407.uteri.ui.screen.TimerViewModel

@Composable
fun TimerScreen(
    onNavigateBack: () -> Unit,
    navController: NavController,
    viewModel: TimerViewModel

) {
    val hoursInput by viewModel.hoursInput.collectAsState()
    val minutesInput by viewModel.minutesInput.collectAsState()
    val remainingTime by viewModel.remainingTime.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val showTimerEndDialog by viewModel.showTimerEndDialog.collectAsState()

    //dialog
    if (showTimerEndDialog) {
        TimerEndDialog(
            onDismiss = { viewModel.dismissDialog() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFae8),
                        Color(0xFFFFE1EB),
                        Color(0xFFFFFae8)
                    )
                )
            )
    ) {
        Scaffold(
            bottomBar = { Navbar(navController) },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.Timer),
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 30.dp, bottom = 50.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                // main input box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(UterUsPink, UterUsYellow)
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

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = hoursInput,
                                onValueChange = { viewModel.updateHours(it) },
                                label = { Text("Hours") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .width(120.dp)
                                    .background(colorResource(id = R.color.light_pink), RoundedCornerShape(4.dp)),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.light_pink),
                                    unfocusedContainerColor = colorResource(id = R.color.light_pink),
                                    disabledContainerColor = colorResource(id = R.color.light_pink)
                                )
                            )

                            Spacer(modifier = Modifier.width(30.dp))

                            OutlinedTextField(
                                value = minutesInput,
                                onValueChange = { viewModel.updateMinutes(it) },
                                label = { Text("Minutes") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .width(120.dp)
                                    .background(colorResource(id = R.color.light_pink), RoundedCornerShape(4.dp)),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = colorResource(id = R.color.light_pink),
                                    unfocusedContainerColor = colorResource(id = R.color.light_pink),
                                    disabledContainerColor = colorResource(id = R.color.light_pink)
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                // default buttons
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 32.dp)
                ) {
                    Text(
                        text = "Defaults:",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(8L, 4L, 2L).forEach { hrs ->
                            Button(
                                onClick = { viewModel.startTimerWithHours(hrs) },
                                shape = CircleShape,
                                modifier = Modifier.size(75.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF69B4)
                                )
                            ) {
                                Text("$hrs h", fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.width(22.dp))
                        }
                    }
                }

                // countdown display
                val displayHours = remainingTime / 3600
                val displayMinutes = (remainingTime % 3600) / 60
                val displaySeconds = remainingTime % 60

                Text(
                    text = String.format("%02d:%02d:%02d", displayHours, displayMinutes, displaySeconds),
                    fontSize = 64.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                //start/stop button
                Button(
                    onClick = {
                        if (!isRunning) {
                            viewModel.startTimerFromInputs()
                        } else {
                            viewModel.stopTimer()
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .width(180.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6489)
                    )
                ) {
                    Text(text = if (isRunning) "stop" else "start", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))
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
        containerColor = Color(0xFFFFF0F5),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 0.dp
    )
}

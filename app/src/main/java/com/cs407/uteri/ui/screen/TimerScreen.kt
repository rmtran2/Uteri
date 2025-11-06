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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.cs407.uteri.R

@Composable
fun TimerScreen(
    onNavigateBack: () -> Unit
) {
    var hoursInput by remember { mutableStateOf("0") }
    var minutesInput by remember { mutableStateOf("0") }
    var remainingTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Function to start the timer
    fun startTimer(hours: Long, minutes: Long = 0) {
//        remainingTime = hours * 3600 + minutes * 60
//        hoursInput = hours.toString()
//        minutesInput = minutes.toString()
//        isRunning = true
//        scope.launch {
//            while (remainingTime > 0 && isRunning) {
//                delay(1000)
//                remainingTime--
//            }
//            isRunning = false
//        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Timer title at the very top
        Text(
            text = stringResource(id = R.string.Timer),
            fontSize = 48.sp,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 110.dp),
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Box with pink background, rounded corners and shadow
        Box(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.pink), shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // "Enter Time" label
                Text(
                    text = stringResource(id = R.string.enter_time_label),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                // Row for inputs
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour input with light pink background
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

                    // Minute input with light pink background
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = "Defaults:",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(8L, 4L, 2L).forEach { hrs ->
                    Button(
                        onClick = { startTimer(hrs, 0) },
                        shape = CircleShape,
                        modifier = Modifier.size(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.pink))
                    ) {
                        Text("$hrs h", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }

        // Countdown timer
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
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Start/Stop button - already centered by the parent Column
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
                .width(180.dp)
        ) {
            Text(
                if (isRunning) "Stop" else "Start",
                fontSize = 20.sp
            )
        }
    }
}
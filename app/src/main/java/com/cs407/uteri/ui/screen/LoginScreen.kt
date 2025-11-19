package com.cs407.uteri.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs407.uteri.R
import com.cs407.uteri.auth.EmailResult
import com.cs407.uteri.auth.PasswordResult
import com.cs407.uteri.auth.checkEmail
import com.cs407.uteri.auth.checkPassword
import com.cs407.uteri.auth.signIn


@Composable
fun ErrorMessage(error: String?) {
    if (error != null) {
        Text(text = error, color = Color.Red)
    }
}

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error: String? by remember { mutableStateOf(null) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFCE4EC),
                            Color(0xFFE3F2FD)
                        )
                    )
                ).padding(24.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFFFF0F5)
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
                    color = Color(0xFFFF6489),
                    fontWeight = FontWeight.SemiBold
                )
                ErrorMessage(error)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(Modifier.padding(16.dp))

                Button(
                    onClick = {

                        val emailValidation = checkEmail(email)

                        val passwordValidation = checkPassword(password)
                        var newError: String? = ""


                        if (emailValidation != EmailResult.Valid) {
                            if (emailValidation == EmailResult.Empty) {
                                newError = "Must input email"
                            } else {
                                newError = "Invalid email."
                            }
                        } else if (passwordValidation != PasswordResult.Valid) {
                            if (passwordValidation == PasswordResult.Empty) {
                                newError = "Must input password."
                            } else if (passwordValidation == PasswordResult.Short) {
                                 newError = "Password too short."
                            } else {
                                  newError = "Invalid password."
                            }
                        }
                        // If both valid, call signIn()
                        if (newError != null && newError.isEmpty()) {
                            error = null
                            signIn(email, password, context, onNavigateToHome)
                        } else {
                            error = newError
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6489),
                        contentColor = Color(0xFFFFE1EB)
                    )
                ) {
                    Text("Sign in")
                }
            }
        }
    }

}
package com.cs407.uteri.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

fun signIn(
    email: String,
    password: String,
    context: Context,
    onNavigateToHome: () -> Unit
    //any other callback function or parameters if you want
) {
    val auth = Firebase.auth
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success
                val user = auth.currentUser
                onNavigateToHome()
            } else {
                // Sign in failed, try creating account
                createAccount(email, password, context,  onNavigateToHome)
            }
        }
}

/**
 * Create new Firebase account with email and password
 */
fun createAccount(
    email: String,
    password: String,
    context: Context,
    onNavigateToHome: () -> Unit
    //any other callback function or parameters if you want
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { result ->
            val user = auth.currentUser
            onNavigateToHome()
        }
        .addOnFailureListener { exception ->
            Log.e("AUTH", "Sign-in failed", exception)
            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }
}

enum class EmailResult {
    Valid,
    Empty,
    Invalid,
}

fun checkEmail(email: String): EmailResult {
    if (email.isEmpty())
        return EmailResult.Empty
    // 1. username of email should only contain "0-9, a-z, _, A-Z, ."
    // 2. there is one and only one "@" between username and server address
    // 3. there are multiple domain names with at least one top-level domain
    // 4. domain name "0-9, a-z, -, A-Z" (could not have "_" but "-" is valid)
    // 5. multiple domain separate with '.'
    // 6. top level domain should only contain letters and at lest 2 letters
    // Remind students this email check only valid for this course
    val pattern = Regex("^[\\w.]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")
    return if (pattern.matches(email)) EmailResult.Valid else EmailResult.Invalid
}

enum class PasswordResult {
    Valid,
    Empty,
    Short,
    Invalid
}

fun checkPassword(password: String): PasswordResult {
    // 1. password should contain at least one uppercase letter, lowercase letter, one digit
    // 2. minimum length: 5
    if (password.isEmpty())
        return PasswordResult.Empty
    if (password.length < 5)
        return PasswordResult.Short
    if (Regex("\\d+").containsMatchIn(password) &&
        Regex("[a-z]+").containsMatchIn(password) &&
        Regex("[A-Z]+").containsMatchIn(password)
    )
        return PasswordResult.Valid
    return PasswordResult.Invalid
}
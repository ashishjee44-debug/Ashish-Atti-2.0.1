package com.example.ashishatte

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.ashishatte.auth.AuthService
import com.example.ashishatte.data.AttendanceRepository
import com.example.ashishatte.ui.AdminScreen
import com.example.ashishatte.ui.EmployeeScreen
import kotlinx.coroutines.launch

@Composable
fun AttendanceApp() {
    val auth = remember { AuthService() }
    val repo = remember { AttendanceRepository() }
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signedIn by remember { mutableStateOf(auth.isSignedIn()) }
    var isAdmin by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    var screen by remember { mutableStateOf("home") }

    if (!signedIn) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Ashish Atte", style = MaterialTheme.typography.headlineLarge)
            Text("Secure GPS attendance")
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(email, { email = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(password, { password = it }, label = { Text("Password") }, singleLine = true, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            Button(
                enabled = !loading && email.isNotBlank() && password.isNotBlank(),
                onClick = {
                    scope.launch {
                        loading = true; error = ""
                        try {
                            auth.signIn(email, password)
                            isAdmin = auth.isAdmin()
                            signedIn = true
                        } catch (e: Exception) {
                            error = e.message ?: "Login failed. Check email/password."
                        } finally { loading = false }
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) { Text(if (loading) "Signing in..." else "Sign In") }
            if (error.isNotBlank()) { Spacer(Modifier.height(10.dp)); Text(error, color = MaterialTheme.colorScheme.error) }
        }
        return
    }

    when (screen) {
        "employee" -> EmployeeScreen(onBack = { screen = "home" }, onSignOut = { auth.signOut(); signedIn = false; screen = "home" })
        "admin" -> AdminScreen(onBack = { screen = "home" }, onSignOut = { auth.signOut(); signedIn = false; screen = "home" })
        else -> Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
            Text("Welcome", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(18.dp))
            Button({ screen = "employee" }, Modifier.fillMaxWidth()) { Text("Employee Attendance") }
            if (isAdmin) {
                Spacer(Modifier.height(10.dp))
                OutlinedButton({ screen = "admin" }, Modifier.fillMaxWidth()) { Text("Admin Dashboard") }
            }
            Spacer(Modifier.height(10.dp))
            TextButton({ auth.signOut(); signedIn = false }) { Text("Sign Out") }
        }
    }
}

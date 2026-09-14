package com.example.ashishatte.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthService(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email.trim(), password).await()
    }

    suspend fun isAdmin(): Boolean = auth.currentUser?.getIdToken(false)?.await()?.claims?.get("admin") == true

    fun isSignedIn(): Boolean = auth.currentUser != null
    fun signOut() = auth.signOut()
    fun uid(): String? = auth.currentUser?.uid
}

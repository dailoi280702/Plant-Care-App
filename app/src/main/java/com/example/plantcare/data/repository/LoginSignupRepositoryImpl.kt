package com.example.plantcare.data.repository

import com.example.plantcare.domain.repository.LoginSignupRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resumeWithException

class LoginSignupRepositoryImpl(
  private val auth: FirebaseAuth
) : LoginSignupRepository {

  override suspend fun signupWithEmailAndPassword(
    email: String,
    password: String
  ) {
    withContext(Dispatchers.IO) {
      auth.createUserWithEmailAndPassword(email, password).await()
    }
  }

  override suspend fun loginWithEmailAndPassword(email: String, password: String) {
    withContext(Dispatchers.IO) {
      auth.signInWithEmailAndPassword(email, password).await()
    }
  }

  override suspend fun loginWithGoogle() {
//    TODO("Not yet implemented")
  }

  override suspend fun loginWithFacebook() {
//    TODO("Not yet implemented")
  }

  override suspend fun loginWithTwitter() {
//    TODO("Not yet implemented")
  }

  override suspend fun logout() {
    auth.signOut()
  }
}

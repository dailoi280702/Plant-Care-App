package com.example.plantcare.data.repository

import android.util.Log
import com.example.plantcare.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
  private val auth: FirebaseAuth
) : AuthenticationRepository {

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
    Log.d("check_logout_tag", "USER LOGGED OUT")
  }

  override fun isUserLogedin(): Boolean {
    return auth.currentUser != null
  }
}

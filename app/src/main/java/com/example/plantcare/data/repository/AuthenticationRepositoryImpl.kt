package com.example.plantcare.data.repository

import android.provider.ContactsContract
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.repository.AuthenticationRepository
import com.example.plantcare.domain.utils.LoginSignupArgumentException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl(
  private val auth: FirebaseAuth
) : AuthenticationRepository {
  
  override suspend fun signupWithEmailAndPassword(
    email: String,
    password: String,
    confirmPassword: String
  ) = flow {
    
    if (email.isBlank() || password.isBlank()) {
      emit(DataState.Error("Please enter both email and password!"))
      return@flow
    }
    if (confirmPassword.isBlank()) {
      emit(DataState.Error("Please confirm your password!"))
      return@flow
    }
    if (password != confirmPassword) {
      emit(DataState.Error("Those password doesn't match!"))
      return@flow
    }
    
    emit(DataState.Loading)
    try {
      auth.createUserWithEmailAndPassword(email, password).await()
    } catch (e: Exception) {
      emit(DataState.Error(e.message ?: "Sign up failed!"))
      return@flow
    }
    emit(DataState.Success(null))
  }
  
  override suspend fun loginWithEmailAndPassword(email: String, password: String) = flow {
    
    if (email.isBlank() || password.isBlank()) {
      emit(DataState.Error("Please enter both email and password"))
      return@flow
    }
    
    emit(DataState.Loading)
    try {
      auth.signInWithEmailAndPassword(email, password).await()
    } catch (e: Exception) {
      emit(DataState.Error(e.message ?: "Log In failed!"))
      return@flow
    }
    emit(DataState.Success(null))
  }
  
  override suspend fun loginWithGoogle(credential: AuthCredential) = flow {
    
    emit(DataState.Loading)
    try {
      auth.signInWithCredential(credential).await()
    } catch (e: Exception) {
      emit(DataState.Error(e.message ?: "Log In with Google failed!"))
      return@flow
    }
    emit(DataState.Success(null))
  }
  
  override suspend fun loginWithFacebook() = flow {
    
    emit(DataState.Loading)
    try {
      emit(DataState.Error("Feature not implemented yet!"))
      return@flow
    } catch (e: Exception) {
      emit(DataState.Error(e.message ?: "Log In with Facebook failed!"))
      return@flow
    }
    emit(DataState.Success(null))
  }
  
  override suspend fun loginWithTwitter() = flow {
    
    emit(DataState.Loading)
    try {
      emit(DataState.Error("Feature not implemented yet!"))
      return@flow
    } catch (e: Exception) {
      emit(DataState.Error(e.message ?: "Log In with Twitter failed!"))
      return@flow
    }
    emit(DataState.Success(null))
  }
  
  override suspend fun logout() {
    auth.signOut()
  }
  
  override fun isUserLogedin(): Boolean {
    return auth.currentUser != null
  }
}

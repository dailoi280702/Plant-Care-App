package com.example.plantcare.domain.repository

import com.example.plantcare.data.utils.DataState
import com.example.plantcare.presentation.login.LoginSignupEvent
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
  suspend fun signupWithEmailAndPassword(email: String, password: String, confirmPassword: String): Flow<DataState<Void?>>
  suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<DataState<Void?>>
  suspend fun loginWithGoogle(credential: AuthCredential): Flow<DataState<Void?>>
  suspend fun loginWithFacebook(): Flow<DataState<Void?>>
  suspend fun loginWithTwitter(): Flow<DataState<Void?>>
  suspend fun logout()
  fun isUserLogedin(): Boolean
}
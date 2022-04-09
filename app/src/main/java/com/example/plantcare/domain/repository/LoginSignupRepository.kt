package com.example.plantcare.domain.repository

interface LoginSignupRepository {
  suspend fun signupWithEmailAndPassword(email: String, password: String)
  suspend fun loginWithEmailAndPassword(email: String, password: String)
  suspend fun loginWithGoogle()
  suspend fun loginWithFacebook()
  suspend fun loginWithTwitter()
  suspend fun logout()
}
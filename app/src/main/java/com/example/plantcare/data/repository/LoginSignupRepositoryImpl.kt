package com.example.plantcare.data.repository

import com.example.plantcare.domain.repository.LoginSignupRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class LoginSignupRepositoryImpl(
  private val auth: FirebaseAuth
) : LoginSignupRepository {

  override suspend fun signupWithEmailAndPassword(
    email: String,
    password: String
  ) {
    auth.createUserWithEmailAndPassword(email, password).await()
  }

  override suspend fun loginWithEmailAndPassword(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password).await()
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

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
  // fast path
  if (isComplete) {
    val e = exception
    return if (e == null) {
      if (isCanceled) {
        throw CancellationException(
          "Task $this was cancelled normally."
        )
      } else {
        result
      }
    } else {
      throw e
    }
  }

  return suspendCancellableCoroutine { cont ->
    addOnCompleteListener {
      val e = exception
      if (e == null) {
        if (isCanceled) cont.cancel() else cont.resume(result) {
          throw CancellationException("Task canceled")
        }
      } else {
        cont.resumeWithException(e)
      }
    }
  }
}
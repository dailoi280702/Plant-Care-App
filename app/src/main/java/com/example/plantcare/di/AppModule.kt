package com.example.plantcare.di

import com.example.plantcare.domain.use_case.logIn_signUp.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideAuth(): FirebaseAuth{
    return Firebase.auth
  }

  @Provides
  @Singleton
  fun provideLoginSignupUsecases(auth: FirebaseAuth): LoginSignupUsecases {
    return LoginSignupUsecases(
      loginWithEmailAndPassword = LoginWithEmailAndPassword(auth = auth),
      loginWithFacebook = LoginWithFacebook(auth = auth),
      loginWithGoogle = LoginWithGoogle(auth = auth),
      loginWithTwitter = LoginWithTwitter(auth = auth),
      logout = Logout(auth = auth),
      signInWithEmailAndPassword = SignInWithEmailAndPassword(auth = auth)
    )
  }
}
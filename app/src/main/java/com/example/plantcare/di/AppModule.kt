package com.example.plantcare.di

import com.example.plantcare.data.repository.AuthenticationRepositoryImpl
import com.example.plantcare.domain.repository.AuthenticationRepository
import com.example.plantcare.domain.use_case.authentication.*
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
  fun provideLoginSignupRepository(auth: FirebaseAuth) : AuthenticationRepository {
    return AuthenticationRepositoryImpl(auth)
  }

  @Provides
  @Singleton
  fun provideLoginSignupUseCases(repository: AuthenticationRepository): LoginSignupUsecases {
    return LoginSignupUsecases(
      loginWithEmailAndPassword = LoginWithEmailAndPassword(repository = repository),
      loginWithFacebook = LoginWithFacebook(repository = repository),
      loginWithGoogle = LoginWithGoogle(repository = repository),
      loginWithTwitter = LoginWithTwitter(repository = repository),
      logout = Logout(repository = repository),
      signupWithEmailAndPassword = SignupWithEmailAndPassword(repository = repository),
      isUserLogedin = IsUserLogedin(repository = repository)
    )
  }
}
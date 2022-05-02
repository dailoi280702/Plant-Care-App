package com.example.plantcare.di

import com.example.plantcare.data.repository.AuthenticationRepositoryImpl
import com.example.plantcare.data.repository.PlantRepositoryImpl
import com.example.plantcare.domain.repository.AuthenticationRepository
import com.example.plantcare.domain.repository.PlantRepository
import com.example.plantcare.domain.use_case.authentication.*
import com.example.plantcare.domain.use_case.plant.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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
  fun provideAuth() = FirebaseAuth.getInstance()

  @Provides
  @Singleton
  fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

  @Provides
  @Singleton
  fun provideFirebaseStorage(): StorageReference = Firebase.storage.reference

  @Provides
  @Singleton
  fun provideUserID(auth: FirebaseAuth): String? = auth.currentUser?.uid

  @Provides
  @Singleton
  fun provideLoginSignupRepository(auth: FirebaseAuth): AuthenticationRepository =
    AuthenticationRepositoryImpl(auth)

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

  @Provides
  @Singleton
  fun providePlantsRef(
    db: FirebaseFirestore,
    userID: String?
  ): DocumentReference = db.collection("test_plants").document(userID ?: "no_one")

  @Provides
  @Singleton
  fun providePlantRepository(
    plantRef: DocumentReference,
    storageRef: StorageReference,
    userId: String?
  ): PlantRepository {
    return PlantRepositoryImpl(plantRef = plantRef, storageRef = storageRef, userId = userId)
  }

  @Provides
  @Singleton
  fun providePlantUseCases(repository: PlantRepository): PlantUseCases {
    return PlantUseCases(
      getPlants = GetPlants(repository = repository),
      getPlant = GetPlant(repository = repository),
      addPlant = AddPlant(repository = repository),
      updatePlant = UpdatePlant(repository = repository),
      deletePlant = DeletePlant(repository = repository)
    )
  }
}
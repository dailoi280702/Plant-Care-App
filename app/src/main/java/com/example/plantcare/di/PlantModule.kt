package com.example.plantcare.di

import com.example.plantcare.data.repository.PlantRepositoryImpl
import com.example.plantcare.domain.repository.PlantRepository
import com.example.plantcare.domain.use_case.plant.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantModule {

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
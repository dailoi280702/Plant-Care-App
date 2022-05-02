package com.example.plantcare.data.repository

import android.net.Uri
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class PlantRepositoryImpl(
  private val plantRef: DocumentReference,
  private val storageRef: StorageReference,
  private val userId: String?
) : PlantRepository {
  override fun getPlants() = callbackFlow {
    val mPlantsCollection = plantRef.collection("plants").orderBy("name")
    val snapshotListener = mPlantsCollection.addSnapshotListener { snapshot, e ->
      val data = if (snapshot != null) {
        try {
          val plants = snapshot.toObjects(Plant::class.java)
          Success(plants)
        } catch (e: Exception) {
          Error(e.message.toString())
        }
      } else {
        Error(e?.message.toString())
      }
      trySend(data).isSuccess
    }

    awaitClose {
      snapshotListener.remove()
    }
  }

  override suspend fun getPlant(id: String) = flow {
    try {
      emit(Loading)
      val snapshot = plantRef.collection("plants").document(id).get().await()
      val plant = snapshot.toObject(Plant::class.java)
      emit(Success(plant))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun addPlant(plant: Plant) = flow {
    try {
      emit(Loading)
      plantRef.collection("plants").add(plant)
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
  override suspend fun addPlant(plant: Plant, uri: Uri?) = flow {
    if (plant.name.isNullOrEmpty() || plant.name.isNullOrBlank()) {
      emit(Error("Please enter plant's name"))
      return@flow
    }
    if (uri == null) {
      emit(Error("Please choose plant's picture"))
      return@flow
    }

    try {
      emit(Loading)
      val id = plantRef.collection("plants").add(plant).await().id
      getPlantImageRef(id).putFile(uri).await()
      val url = getPlantImageRef(id).downloadUrl.await().toString()
      plantRef.collection("plants").document(id).set(
        plant.copy(
          id = id,
          imageURL = url,
          dateAdded = System.currentTimeMillis()
        )
      ).await()
      val snapshot = plantRef.collection("plants").document(id).get().await()
      val newPlant = snapshot.toObject(Plant::class.java)
      emit(Success(newPlant))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun deletePlant(id: String) = flow {
    try {
      emit(Loading)
      //todo
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun updatePlant(plant: Plant, uri: Uri?) = flow {
    if (plant.name.isNullOrEmpty() || plant.name.isNullOrBlank()) {
      emit(Error("Plant's name cannot be empty"))
      return@flow
    }

    try {
      emit(Loading)
      val id = plant.id!!
      var url = plant.imageURL!!
      if (uri != null) {
        getPlantImageRef(id).putFile(uri).await()
        url = getPlantImageRef(id).downloadUrl.await().toString()
      }
      plantRef.collection("plants").document(id).set(
        plant.copy(
          imageURL = url,
        )
      ).await()
      val snapshot = plantRef.collection("plants").document(id).get().await()
      val newPlant = snapshot.toObject(Plant::class.java)
      emit(Success(newPlant))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override fun getPlantImageRef(id: String) = storageRef.child("test_iamges/$userId/$id")

}
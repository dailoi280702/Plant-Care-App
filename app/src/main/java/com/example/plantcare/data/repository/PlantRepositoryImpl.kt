package com.example.plantcare.data.repository

import android.net.Uri
import com.example.plantcare.core.Constants.PLANTS
import com.example.plantcare.core.Constants.TODOS
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.PlantOrder
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
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
  private val db: FirebaseFirestore,
  private val userId: String?
) : PlantRepository {
  
  override fun getPlants(plantOrder: PlantOrder?, limit: Long?) = callbackFlow {
    val direction =
      if (plantOrder?.orderType is OrderType.Ascending) Query.Direction.ASCENDING else Query.Direction.DESCENDING
    var mPlantsCollection =
      plantRef.collection(PLANTS).orderBy(plantOrder?.orderName ?: "name", direction)
    limit?.let {
      mPlantsCollection = mPlantsCollection.limit(limit)
    }
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
      val snapshot = plantRef.collection(PLANTS).document(id).get().await()
      val plant = snapshot.toObject(Plant::class.java)
      emit(Success(plant))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
  
  override suspend fun addPlant(plant: Plant) = flow {
    try {
      emit(Loading)
      plantRef.collection(PLANTS).add(plant)
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
      val id = plantRef.collection(PLANTS).add(plant).await().id
      getPlantImageRef(id).putFile(uri).await()
      val url = getPlantImageRef(id).downloadUrl.await().toString()
      val newPlant = plant.copy(
        id = id,
        imageURL = url,
        dateAdded = System.currentTimeMillis()
      )
      plantRef.collection(PLANTS).document(id).set(
        newPlant
      ).await()
      emit(Success(newPlant))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
  
  override suspend fun deletePlant(id: String) = flow {
    try {
      emit(Loading)
      plantRef.collection(PLANTS).document(id).delete().await()
      getPlantImageRef(id).delete().await()
      emit(Success(null))
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
      
      uri?.let {
        getPlantImageRef(id).putFile(uri).await()
        url = getPlantImageRef(id).downloadUrl.await().toString()
        plantRef.collection(PLANTS).document(id).update("imageURL", url).await()
      }
  
      val updatedPlant = plant.copy(imageURL = url)
      plantRef.collection(PLANTS).document(plant.id!!).set(updatedPlant).await()
      emit(Success(updatedPlant))
      
      plantRef.collection(TODOS).whereEqualTo("plantId", plant.id).get().addOnCompleteListener {
        if (it.isSuccessful) {
          for (doc in it.result) {
            plantRef.collection(TODOS).document(doc.id).update("plantName", plant.name)
          }
        }
      }
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
  
  override fun getPlantImageRef(id: String) = storageRef.child("test_iamges/$userId/$id")
  
  override suspend fun getPlantName(id: String) = callbackFlow {
    val snapshotListener =
      plantRef.collection(PLANTS).document(id).addSnapshotListener { snapshot, e ->
        if (e != null) {
          trySend(e.message).isSuccess
        }
        
        if (snapshot != null && snapshot.exists()) {
          val name = snapshot.getString("name")
          trySend(name!!).isSuccess
        }
      }
    
    awaitClose {
      snapshotListener.remove()
    }
  }
}
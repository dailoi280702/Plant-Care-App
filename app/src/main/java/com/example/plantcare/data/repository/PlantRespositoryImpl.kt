package com.example.plantcare.data.repository

import android.util.Log
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.data.utils.DataState.Error
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class PlantRespositoryImpl(
  private val plantRef: DocumentReference
) : PlantRepository {
  override fun getPlants() = callbackFlow {
    val mPlantsCollection = plantRef.collection("plants").orderBy("name")
    val snapshotListener = mPlantsCollection.addSnapshotListener { snapshot, e ->
      val data = if (snapshot != null) {
        try {
//          val plants = snapshot.toObjects(Plant::class.java)
          val plants = mutableListOf<Plant>()
          val documents = snapshot.documents
          documents.onEach {
            val plant = it.toObject(Plant::class.java)
            if (plant != null) {
              plant.id = it.id
              plants.add(plant)
            }
          }
          DataState.Success(plants)
        } catch (e: Exception) {
          Log.d("logggg", e.message ?: "idk")
          Error(e?.message.toString())
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
      emit(DataState.Loading)
    } catch (e: Exception) {
      //todo
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun addPlant(plant: Plant) = flow {
    val db = FirebaseFirestore.getInstance()
    Log.d("logggg", "loading_first")
    try {
      emit(DataState.Loading)
      Log.d("logggg", "loading")
      plantRef.collection("plants").add(plant)
      Log.d("logggg", "finish")
    } catch (e: Exception) {
      Log.d("logggg", "err")
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun deletePlant(id: String) = flow {
    try {
      emit(DataState.Loading)
      //todo
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun updatePlant(plant: Plant) = flow {
    try {
      emit(DataState.Loading)
      //todo
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
}
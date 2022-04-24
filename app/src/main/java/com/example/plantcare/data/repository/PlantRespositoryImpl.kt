package com.example.plantcare.data.repository

import com.example.plantcare.data.utils.DataState
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

//@Singleton
class PlantRespositoryImpl(
  private val userID: String?
) : PlantRepository {
    override fun getPlants() = callbackFlow {
    val mPlantsCollection = FirebaseFirestore.getInstance().collection("plants")
    val snapshotListener = mPlantsCollection.addSnapshotListener { snapshot, e ->
      val data = if (snapshot != null) {
        val plants = snapshot.toObjects(Plant::class.java)
        DataState.Success(plants)
      } else {
        DataState.Error(e?.message.toString())
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
    try {
      emit(DataState.Loading)
      //todo
    } catch (e: Exception) {
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
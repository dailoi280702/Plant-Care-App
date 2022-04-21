package com.example.plantcare.data.repository

import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow

class PlantRespositoryImpl(
  private val userID: String?
) : PlantRepository {
  override fun getPlants(): Flow<List<Plant>> {
    TODO("Not yet implemented")
//    return null
  }

  override suspend fun getPlant(id: String): Plant? {
//    TODO("Not yet implemented")
    return null
  }

  override suspend fun addPlant(plant: Plant) {
//    TODO("Not yet implemented")
  }

  override suspend fun deletePlant(id: String) {
//    TODO("Not yet implemented")
  }

  override suspend fun updatePlant(plant: Plant) {
//    TODO("Not yet implemented")
  }
}
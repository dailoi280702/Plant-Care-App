package com.example.plantcare.domain.repository

import com.example.plantcare.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

  fun getPlants(): Flow<List<Plant>>

  suspend fun getPlant(id: String): Plant?

  suspend fun addPlant(plant: Plant)

  suspend fun deletePlant(id: String)

  suspend fun updatePlant(plant: Plant)
}
package com.example.plantcare.domain.repository

import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

  fun getPlants(): Flow<DataState<List<Plant>>>

  suspend fun getPlant(id: String): Flow<DataState<Void?>>

  suspend fun addPlant(plant: Plant): Flow<DataState<Void?>>

  suspend fun deletePlant(id: String): Flow<DataState<Void?>>

  suspend fun updatePlant(plant: Plant): Flow<DataState<Void?>>
}
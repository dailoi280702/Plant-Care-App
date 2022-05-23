package com.example.plantcare.domain.repository

import android.net.Uri
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.utils.PlantOrder
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

  fun getPlants(plantOrder: PlantOrder?, limit: Long?): Flow<DataState<List<Plant>>>

  suspend fun getPlant(id: String): Flow<DataState<Plant?>>

  suspend fun addPlant(plant: Plant): Flow<DataState<Plant?>>

  suspend fun deletePlant(id: String): Flow<DataState<Void?>>

  suspend fun updatePlant(plant: Plant, uri: Uri?): Flow<DataState<Plant?>>

  fun getPlantImageRef(id: String): StorageReference

  suspend fun addPlant(plant: Plant, uri: Uri?): Flow<DataState<Plant?>>

  suspend fun getPlantName(id: String): Flow<String?>
}
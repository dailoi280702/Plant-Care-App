package com.example.plantcare.domain.repository

import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.utils.TaskOrder
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
  fun getTasks(taskOrder: TaskOrder?): Flow<DataState<List<PlantTask>>>

  fun getTasksByPlantID(plantId: String): Flow<DataState<List<PlantTask>>>

  suspend fun getTask(id: String): Flow<DataState<PlantTask?>>

  suspend fun addTask(task: PlantTask): Flow<DataState<PlantTask?>>

  suspend fun deleteTask(id: String): Flow<DataState<Void?>>

  suspend fun updateTask(task: PlantTask): Flow<DataState<PlantTask?>>
}
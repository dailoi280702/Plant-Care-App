package com.example.plantcare.domain.repository

import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
  fun getTasks(): Flow<DataState<List<Todo>>>

  fun getTasksByPlantID(plantId: String): Flow<DataState<List<Todo>>>

  suspend fun getTask(id: String): Flow<DataState<Todo?>>

  suspend fun addTask(task: Todo): Flow<DataState<Todo?>>

  suspend fun deleteTask(id: String): Flow<DataState<Void?>>

  suspend fun updateTask(task: Todo): Flow<DataState<Todo?>>
  
  suspend fun markTodoAsDone(todo: Todo)
}
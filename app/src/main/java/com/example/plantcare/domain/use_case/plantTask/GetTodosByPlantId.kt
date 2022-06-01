package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.data.utils.DataState.Success
import com.example.plantcare.domain.repository.TaskRepository
import kotlinx.coroutines.flow.map

class GetTodosByPlantId(
  private val repository: TaskRepository
) {
  operator fun invoke(plantId: String) =
    repository.getTasksByPlantID(plantId = plantId).map { dataState ->
      when (dataState) {
        is Success -> {
          Success(dataState.data.sortedBy { it.dueDay }.sortedBy { it.important }.sortedBy { it.done })
        }
        else -> {
          dataState
        }
      }
    }
}
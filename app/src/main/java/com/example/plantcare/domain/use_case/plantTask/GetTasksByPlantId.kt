package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.repository.TaskRepository

class GetTasksByPlantId(
  private val repository: TaskRepository
) {
  operator fun invoke(plantId: String) = repository.getTasksByPlantID(plantId = plantId)
}
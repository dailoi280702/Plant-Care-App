package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.repository.TaskRepository

class AddTask(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(task: PlantTask) = repository.addTask(task = task)
}
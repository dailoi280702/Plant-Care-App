package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.utils.TaskOrder

class GetTasks(
  private val repository: TaskRepository
) {
  operator fun invoke(taskOrder: TaskOrder) = repository.getTasks(taskOrder = taskOrder)
}
package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.repository.TaskRepository

class DeleteTodo(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(id: String) = repository.deleteTask(id = id)
}
package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.repository.TaskRepository

class UpdateTodo(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(task: Todo) = repository.updateTask(task = task)
}
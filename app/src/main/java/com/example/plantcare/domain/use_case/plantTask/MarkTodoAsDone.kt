package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.repository.TaskRepository

class MarkTodoAsDone(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(todo: Todo) = repository.markTodoAsDone(todo)
}
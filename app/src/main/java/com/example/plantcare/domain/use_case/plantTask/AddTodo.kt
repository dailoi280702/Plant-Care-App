package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.repository.TaskRepository

class AddTodo(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(task: Todo) = repository.addTask(task = task)
}
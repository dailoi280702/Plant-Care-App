package com.example.plantcare.domain.use_case.plantTask;

import com.example.plantcare.domain.repository.TaskRepository;

class GetTodo(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(id: String) = repository.getTask(id = id);
}

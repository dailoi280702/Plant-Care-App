package com.example.plantcare.presentation.todo

import com.example.plantcare.domain.model.Todo

sealed class TodoEvent{
  data class DeleteTodo(val value: Todo): TodoEvent()
  data class UpdateExpandedCard(val value: Todo): TodoEvent()
  data class MarkTodoAsDone(val value: Todo): TodoEvent()
  object RestoreTodo: TodoEvent()
}

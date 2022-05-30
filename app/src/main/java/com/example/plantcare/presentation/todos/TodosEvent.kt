package com.example.plantcare.presentation.todos

import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime

sealed class TodosEvent {
  object ToggleFilterSection: TodosEvent()
  object RefreshTodos: TodosEvent()
  data class UpdateTodoTimes(val value: TodoTime): TodosEvent()
  data class UpdateTodoOrder(val value: TodoOrder): TodosEvent()
}
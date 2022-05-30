package com.example.plantcare.presentation.todos

import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime

data class TodosState(
  val todos: List<Todo> = emptyList(),
  val todoTimes: List<TodoTime> = listOf(TodoTime.Today),
  val todoOrder: TodoOrder = TodoOrder.Default(OrderType.Descending),
  val deletedTodo: Todo? = null,
  val todoOfDialog: Todo? = null,
  val filterSectionVisibility: Boolean = false
)

package com.example.plantcare.presentation.todo

import com.example.plantcare.domain.model.Todo

data class TodoState(
  val deletedTodo: Todo? = null,
  val expandedCard: Todo? = null
)

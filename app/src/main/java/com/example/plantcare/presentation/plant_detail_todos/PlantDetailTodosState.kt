package com.example.plantcare.presentation.plant_detail_todos

import com.example.plantcare.domain.model.Todo

data class PlantDetailTodosState(
  val plantId: String? = null,
  val taskList: List<Todo> = emptyList()
)

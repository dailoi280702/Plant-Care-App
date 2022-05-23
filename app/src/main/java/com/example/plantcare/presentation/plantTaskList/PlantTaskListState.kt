package com.example.plantcare.presentation.plantTaskList

import com.example.plantcare.domain.model.PlantTask

data class PlantTaskListState(
  val plantId: String? = null,
  val taskList: List<PlantTask> = emptyList(),
  val expandedPlantTaskCard: PlantTask? = null,
)

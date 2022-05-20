package com.example.plantcare.presentation.plantTaskList

import com.example.plantcare.domain.model.PlantTask

sealed class PlantTaskListEvent {
  data class UpdateExpandedCard(val task: PlantTask?): PlantTaskListEvent()
  data class DeletePlantTask(val task: PlantTask): PlantTaskListEvent()
  data class MarkAsDone(val task: PlantTask): PlantTaskListEvent()
}

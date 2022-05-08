package com.example.plantcare.domain.model

data class PlantTask(
  var taskId: String? = "",
  var plantId: String? = "",
  val title: String? = "",
  val description: String? = "",
  val timestamp: Long? = -1,
  val important: Int? = 0
)
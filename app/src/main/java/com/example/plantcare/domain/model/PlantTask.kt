package com.example.plantcare.domain.model

import com.google.firebase.Timestamp

data class PlantTask(
  var taskId: String? = "",
  var plantId: String? = "",
  val title: String? = "",
  var dueDay: Timestamp? = null,
  val duration: Int? = 0,
  val important: Int? = 0,
  val repeatable: Boolean = false,
  val done: Boolean = false,
  var overDue: Boolean = false
)

package com.example.plantcare.presentation.add_edit_task

import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.model.Todo

//data class AddEditTaskDialogState(
//  val plantId: String? = null,
//  val title: String = "",
//  val important: Int = 0,
//  val day: Int = 1,
//  val month: Int = 1,
//  val year: Int = 2000,
//  val duration: Duration = Duration.Day(1),
//  val repeatable: Boolean = false
//)

data class AddEditTaskDialogState(
  val plant: Plant? = null,
  val todo: Todo = Todo(),
  val day: Int = 1,
  val month: Int = 1,
  val year: Int = 2000,
  val duration: Duration = Duration.Day(1),
  val visible: Boolean = false
)

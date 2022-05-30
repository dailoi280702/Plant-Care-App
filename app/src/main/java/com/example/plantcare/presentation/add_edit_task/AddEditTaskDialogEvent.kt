package com.example.plantcare.presentation.add_edit_task

sealed class AddEditTaskDialogEvent {
  data class UpdateTitle(val value: String): AddEditTaskDialogEvent()
  data class UpdateDueDate(val day: Int, val month: Int, val year: Int): AddEditTaskDialogEvent()
  data class UpdateDuration(val value: Duration): AddEditTaskDialogEvent()
  data class UpdateDialogVisibility(val value: Boolean) : AddEditTaskDialogEvent()
  object UpdateRepeatable: AddEditTaskDialogEvent()
  object UpdateTaskImportant: AddEditTaskDialogEvent()
  object AddTask: AddEditTaskDialogEvent()
  object UpdateTask: AddEditTaskDialogEvent()
}

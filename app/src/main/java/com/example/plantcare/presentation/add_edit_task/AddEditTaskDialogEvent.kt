package com.example.plantcare.presentation.add_edit_task

sealed class AddEditTaskDialogEvent {
  data class UpdateTitle(val value: String): AddEditTaskDialogEvent()
  data class UpdateDescription(val value: String): AddEditTaskDialogEvent()
  object UpdateTaskImportant: AddEditTaskDialogEvent()
  object AddTask: AddEditTaskDialogEvent()
}

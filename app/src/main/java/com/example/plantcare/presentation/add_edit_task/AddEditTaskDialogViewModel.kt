package com.example.plantcare.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.use_case.plantTask.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskDialogViewModel @Inject constructor(
  private val taskUseCases: TaskUseCases
) : ViewModel() {

  private val _plantTask = mutableStateOf<PlantTask>(PlantTask())
  val plantTask: State<PlantTask> = _plantTask

  private val _dataState = mutableStateOf<DataState<PlantTask?>>(DataState.Success(null))
  val dataState: State<DataState<PlantTask?>> = _dataState

  private val _errorMessage = mutableStateOf<String?>(null)
  val errorMessage: State<String?> = _errorMessage

  private val _eventFLow = MutableSharedFlow<Boolean>()
  val eventFlow = _eventFLow.asSharedFlow()

  fun initTask(task: PlantTask) {
    _plantTask.value = task
  }

  private fun addTask() {
    viewModelScope.launch {
      taskUseCases.addTask(task = plantTask.value).collect {
        _dataState.value = it
        if (it is DataState.Error) {
          _errorMessage.value = it.message
        }
        if (it is DataState.Success) {
          _eventFLow.emit(true)
        }
      }
    }
  }

  fun onEvent(event: AddEditTaskDialogEvent) {
    when (event) {
      is AddEditTaskDialogEvent.UpdateTitle -> {
        _plantTask.value = plantTask.value.copy(
          title = event.value
        )
      }
      is AddEditTaskDialogEvent.UpdateDescription -> {
        _plantTask.value = plantTask.value.copy(
          description = event.value
        )
      }
      is AddEditTaskDialogEvent.UpdateTaskImportant -> {
        val value = plantTask.value.important
        val newValue = (value?.plus(1))?.rem(3) ?: 0
        _plantTask.value = plantTask.value.copy(
          important = newValue
        )
      }
      is AddEditTaskDialogEvent.AddTask -> {
        addTask()
      }
    }
  }
}
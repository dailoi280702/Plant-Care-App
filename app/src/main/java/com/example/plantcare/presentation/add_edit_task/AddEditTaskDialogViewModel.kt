package com.example.plantcare.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.use_case.plantTask.TaskUseCases
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditTaskDialogViewModel @Inject constructor(
  private val taskUseCases: TaskUseCases
) : ViewModel() {

  private val _addEditTaskDialogState = mutableStateOf(AddEditTaskDialogState())
  val addEditTaskDialogState: State<AddEditTaskDialogState> = _addEditTaskDialogState

//  private val _plantTask = mutableStateOf<PlantTask>(PlantTask())
//  val plantTask: State<PlantTask> = _plantTask

  private val _dataState = mutableStateOf<DataState<PlantTask?>>(DataState.Success(null))
  val dataState: State<DataState<PlantTask?>> = _dataState

  private val _errorMessage = mutableStateOf<String?>(null)
  val errorMessage: State<String?> = _errorMessage

  private val _eventFLow = MutableSharedFlow<Boolean>()
  val eventFlow = _eventFLow.asSharedFlow()

  fun init(plantId: String) {
    val now = Calendar.getInstance()
    _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
      plantId = plantId,
      year = now.get(Calendar.YEAR),
      month = now.get(Calendar.MONTH),
      day = now.get(Calendar.DAY_OF_MONTH)
    )
  }

  private fun checkValidDueDate(day: Int, month: Int, year: Int): Boolean {
    val now = Calendar.getInstance()
    now.add(Calendar.DAY_OF_MONTH, -1)
    val dueDate: Calendar = GregorianCalendar(year, month, day, 0, 0, 0)
    return now.before(dueDate)
  }

  private fun addTask() {
    viewModelScope.launch {
      val value = addEditTaskDialogState.value
      val dueDate: Calendar = GregorianCalendar(
        value.year, value.month, value.day, 23, 59, 59
      )
      taskUseCases.addTask(
        task = PlantTask(
          plantId = value.plantId,
          title = value.title,
          dueDay = Timestamp(dueDate.time),
          duration = value.duration.toInt(),
          important = value.important,
          repeatable = value.repeatable
        )
      ).collect {
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
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          title = event.value
        )
      }
      is AddEditTaskDialogEvent.UpdateDueDate -> {
        if (checkValidDueDate(event.day, event.month, event.year)) {
          _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
            day = event.day,
            month = event.month,
            year = event.year
          )
        }
      }
      is AddEditTaskDialogEvent.UpdateDuration -> {
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          duration = event.value
        )
      }
      is AddEditTaskDialogEvent.UpdateRepeatable -> {
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          repeatable = !addEditTaskDialogState.value.repeatable
        )
      }
      is AddEditTaskDialogEvent.UpdateTaskImportant -> {
        val value = addEditTaskDialogState.value.important
        val newValue = (value.plus(1)).rem(3)
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          important = newValue
        )
      }
      is AddEditTaskDialogEvent.AddTask -> {
        addTask()
      }
    }
  }
}
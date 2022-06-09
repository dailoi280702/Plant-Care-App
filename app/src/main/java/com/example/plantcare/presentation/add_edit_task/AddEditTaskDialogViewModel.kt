package com.example.plantcare.presentation.add_edit_task

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.use_case.plantTask.TodoUseCases
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditTaskDialogViewModel @Inject constructor(
  private val taskUseCases: TodoUseCases
) : ViewModel() {
  
  private val _addEditTaskDialogState = mutableStateOf(AddEditTaskDialogState())
  val addEditTaskDialogState: State<AddEditTaskDialogState> = _addEditTaskDialogState
  
  private val _dataState = mutableStateOf<DataState<Todo?>>(DataState.Success(null))
  val dataState: State<DataState<Todo?>> = _dataState
  
  private val _errorMessage = mutableStateOf<String?>(null)
  val errorMessage: State<String?> = _errorMessage
  
  private val _eventFLow = MutableSharedFlow<Boolean>()
  val eventFlow = _eventFLow.asSharedFlow()

  
  fun init(plant: Plant? = null, todo: Todo? = null) {
    val now = Calendar.getInstance()
    todo?.let {
      now.time = it.dueDay!!.toDate()
    }
    _addEditTaskDialogState.value = AddEditTaskDialogState(
      plant = plant,
      todo = todo ?: Todo(plantId = plant!!.id, plantName = plant.name),
      year = now.get(Calendar.YEAR),
      month = now.get(Calendar.MONTH),
      day = now.get(Calendar.DAY_OF_MONTH),
      duration = Duration.Day(if (todo == null) 1 else todo.duration!!),
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
      val duration = addEditTaskDialogState.value.duration.toInt()
      
      taskUseCases.addTodo(
        task = addEditTaskDialogState.value.todo.copy(
          dueDay = Timestamp(dueDate.time),
          duration = duration
        )
      ).collect {
        _dataState.value = it
        if (it is DataState.Error) {
          _errorMessage.value = it.message
          Log.d("errmsg", it.message)
        }
        if (it is DataState.Success) {
//          _eventFLow.emit(true)
          onEvent(AddEditTaskDialogEvent.UpdateDialogVisibility(false))
        }
      }
    }
  }
  
  private fun updateTask() {
    viewModelScope.launch {
      val value = addEditTaskDialogState.value
      val dueDate: Calendar = GregorianCalendar(
        value.year, value.month, value.day, 23, 59, 59
      )
      val duration = addEditTaskDialogState.value.duration.toInt()
      
      taskUseCases.updateTodo(
        task = addEditTaskDialogState.value.todo.copy(
          dueDay = Timestamp(dueDate.time),
          duration = duration
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
          todo = addEditTaskDialogState.value.todo.copy(title = event.value)
        )
      }
      is AddEditTaskDialogEvent.UpdateDueDate -> {
        if (!checkValidDueDate(event.day, event.month, event.year)) return
        
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          day = event.day,
          month = event.month,
          year = event.year
        )
      }
      is AddEditTaskDialogEvent.UpdateDuration -> {
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          duration = event.value
        )
      }
      is AddEditTaskDialogEvent.UpdateRepeatable -> {
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          todo = addEditTaskDialogState.value.todo.copy(repeatable = !addEditTaskDialogState.value.todo.repeatable)
        )
      }
      is AddEditTaskDialogEvent.UpdateTaskImportant -> {
        val value = addEditTaskDialogState.value.todo.important!!
        val newValue = (value.plus(1)).rem(3)
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(
          todo = addEditTaskDialogState.value.todo.copy(important = newValue)
        )
      }
      is AddEditTaskDialogEvent.AddTask -> {
        addTask()
      }
      is AddEditTaskDialogEvent.UpdateTask -> {
        updateTask()
      }
      is AddEditTaskDialogEvent.UpdateDialogVisibility -> {
        if (addEditTaskDialogState.value.visible == event.value) return
        
        _addEditTaskDialogState.value = addEditTaskDialogState.value.copy(visible = event.value)
      }
    }
  }
}
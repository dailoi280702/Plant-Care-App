package com.example.plantcare.presentation.plantTaskList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.use_case.plantTask.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantTaskListViewModel @Inject constructor(
  private val taskUseCases: TaskUseCases
) : ViewModel() {

  private val _plantTaskListState = mutableStateOf(PlantTaskListState())
  val plantTaskListState: State<PlantTaskListState> = _plantTaskListState

  private val _dataState = mutableStateOf<DataState<List<PlantTask>?>>(DataState.Success(null))
  val dataState: State<DataState<List<PlantTask>?>> = _dataState

  private val _removedTask = mutableStateOf<PlantTask?>(null)
  val removedTask: State<PlantTask?> = _removedTask

  val task = mutableStateOf<PlantTask?>(null)

  fun init(plantId: String) {
    viewModelScope.launch {
      _plantTaskListState.value = plantTaskListState.value.copy(plantId = plantId)
      taskUseCases.getTasksByPlantId(plantId).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _plantTaskListState.value = plantTaskListState.value.copy(taskList = it.data)
        }
      }
    }
  }

  private fun deletePlantTask(task: PlantTask) {
    viewModelScope.launch {
      taskUseCases.deleteTask(task.taskId!!).collectLatest {

      }
    }
  }

  private fun markTaskAsDone(task: PlantTask) {
    viewModelScope.launch {
//      val now = Calendar.getInstance()
//      val dueDay = GregorianCalendar.getInstance()
//      val duration = task.duration!!
//      dueDay.time = task.dueDay!!.toDate()
//
//      while (compareTwoDates(now.time, dueDay.time) >= 0) {
//        dueDay.add(Calendar.DATE, duration)
//      }
//      val list = plantTaskListState.value.taskList.toMutableList()
//      list.remove(task)
//      _plantTaskListState.value = plantTaskListState.value.copy(taskList = list)
//      list.add(task)
//      _plantTaskListState.value = plantTaskListState.value.copy(taskList = list)
//      taskUseCases.updateTask(
//        task.copy(
//          dueDay = Timestamp(dueDay.time)
//        )
//      ).collectLatest {
//
//      }
      taskUseCases.updateTask(task.copy(done = !task.done)).collectLatest {

      }
    }
  }

  fun onEvent(event: PlantTaskListEvent) {
    when (event) {
      is PlantTaskListEvent.UpdateExpandedCard -> {
        if (event.task != plantTaskListState.value.expandedPlantTaskCard) {
          _plantTaskListState.value = plantTaskListState.value.copy(
            expandedPlantTaskCard = event.task
          )
        }
      }
      is PlantTaskListEvent.MarkAsDone -> {
        markTaskAsDone(event.task)
//        _removedTask.value = event.task
      }
      is PlantTaskListEvent.DeletePlantTask -> {
        deletePlantTask(event.task)
//        _removedTask.value = event.task
      }
    }
  }
}
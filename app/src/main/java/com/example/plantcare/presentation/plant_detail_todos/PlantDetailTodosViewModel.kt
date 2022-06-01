package com.example.plantcare.presentation.plant_detail_todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.use_case.plantTask.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailTodosViewModel @Inject constructor(
  private val taskUseCases: TodoUseCases
) : ViewModel() {

  private val _plantDetailTodosState = mutableStateOf(PlantDetailTodosState())
  val plantDetailTodosState: State<PlantDetailTodosState> = _plantDetailTodosState

  private val _dataState = mutableStateOf<DataState<List<Todo>?>>(DataState.Success(null))
  val dataState: State<DataState<List<Todo>?>> = _dataState

  fun init(plantId: String) {
    viewModelScope.launch {
      _plantDetailTodosState.value = plantDetailTodosState.value.copy(plantId = plantId)
      taskUseCases.getTodosByPlantId(plantId).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _plantDetailTodosState.value = plantDetailTodosState.value.copy(taskList = it.data)
        }
      }
    }
  }
}
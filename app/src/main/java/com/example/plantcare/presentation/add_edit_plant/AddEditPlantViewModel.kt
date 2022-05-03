package com.example.plantcare.presentation.add_edit_plant

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _addEditPlantState = mutableStateOf<AddEditPlantState>(AddEditPlantState())
  val addEditPlantState: State<AddEditPlantState> = _addEditPlantState

  private val _dataState = mutableStateOf<DataState<Plant?>>(DataState.Success(null))
  val dataState: State<DataState<Plant?>> = _dataState

  private val _eventFLow = MutableSharedFlow<AddEditPlantUiEvent>()
  val eventFlow = _eventFLow.asSharedFlow()

  private var savePlantJob: Job? = null

  init {
    viewModelScope.launch {
      _addEditPlantState.value = addEditPlantState.value.copy(
        plant = addEditPlantState.value.plant.copy(
          id = savedStateHandle.get<String>("plantId")
        )
      )
      if (addEditPlantState.value.plant.id != "") {
        plantUseCases.getPlant(addEditPlantState.value.plant.id!!).collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _addEditPlantState.value = it.data?.let { plant ->
              addEditPlantState.value.copy(
                plant = plant
              )
            }!!
          }
        }
      }
    }
  }

  fun onEvent(event: AddEditPlantEvent) {
    when (event) {
      is AddEditPlantEvent.ToggleImageSection -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          expandedImage = !addEditPlantState.value.expandedImage
        )
      }
      is AddEditPlantEvent.ToggleInfoSection -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          expandedInfo = !addEditPlantState.value.expandedInfo
        )
      }
      is AddEditPlantEvent.ToggleTasksSection -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          expandedTasks = !addEditPlantState.value.expandedTasks
        )
      }
      is AddEditPlantEvent.ToggleSubFab -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          subFabVisibility = !addEditPlantState.value.subFabVisibility
        )
      }
      is AddEditPlantEvent.EnterName -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          plant = addEditPlantState.value.plant.copy(
            name = event.value
          )
        )
      }
      is AddEditPlantEvent.EnterDescription -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          plant = addEditPlantState.value.plant.copy(
            description = event.value
          )
        )
      }
      is AddEditPlantEvent.ChangeImageUri -> {
        _addEditPlantState.value = addEditPlantState.value.copy(
          imageUri = event.uri
        )
      }
      is AddEditPlantEvent.SavePlant -> {
        if (dataState.value != DataState.Loading) {
          if (addEditPlantState.value.plant.id == "") {
            addPlant()
          } else {
            updatePlant()
          }
        }
      }
    }
  }

  private fun addPlant() {
    viewModelScope.launch {
      plantUseCases.addPlant(
        addEditPlantState.value.plant,
        uri = addEditPlantState.value.imageUri
      ).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _addEditPlantState.value = addEditPlantState.value.copy(
            plant = it.data!!,
          )
        }
        if (it is DataState.Error) {
          _eventFLow.emit(AddEditPlantUiEvent.ShowError(it.message))
        }
      }
    }
  }

  private fun updatePlant() {
    viewModelScope.launch {
      plantUseCases.updatePlant(
        addEditPlantState.value.plant,
        uri = addEditPlantState.value.imageUri
      ).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _addEditPlantState.value = addEditPlantState.value.copy(
            plant = it.data!!,
          )
        }
        if (it is DataState.Error) {
          _eventFLow.emit(AddEditPlantUiEvent.ShowError(it.message))
        }
      }
    }
  }
}
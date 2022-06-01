package com.example.plantcare.presentation.plant_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _plantDetailState = mutableStateOf(PlantDetailState())
  val plantDetailState: State<PlantDetailState> = _plantDetailState

  private val _dataState = mutableStateOf<DataState<Plant?>>(DataState.Success(null))
  val dataState: State<DataState<Plant?>> = _dataState

  private val _eventFLow = MutableSharedFlow<PlantDetailUiEvent>()
  val eventFlow = _eventFLow.asSharedFlow()

  private val _currentPlantTask = mutableStateOf<Todo?>(null)
  val currentPlantTask: State<Todo?> = _currentPlantTask

  init {
    viewModelScope.launch {
      _plantDetailState.value = plantDetailState.value.copy(
        plant = plantDetailState.value.plant.copy(
          id = savedStateHandle.get<String>("plantId")
        )
      )
      if (plantDetailState.value.plant.id != "") {
        plantUseCases.getPlant(plantDetailState.value.plant.id!!).collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _plantDetailState.value = it.data?.let { plant ->
              plantDetailState.value.copy(
                plant = plant
              )
            }!!
          }
        }
      }
    }
  }

  fun onEvent(event: PlantDetailEvent) {
    when (event) {
      is PlantDetailEvent.ToggleImageSection -> {
        _plantDetailState.value = plantDetailState.value.copy(
          expandedImage = !plantDetailState.value.expandedImage
        )
      }
      is PlantDetailEvent.ToggleInfoSection -> {
        _plantDetailState.value = plantDetailState.value.copy(
          expandedInfo = !plantDetailState.value.expandedInfo
        )
      }
      is PlantDetailEvent.ToggleTasksSection -> {
        _plantDetailState.value = plantDetailState.value.copy(
          expandedTasks = !plantDetailState.value.expandedTasks
        )
      }
      is PlantDetailEvent.ToggleSubFab -> {
        _plantDetailState.value = plantDetailState.value.copy(
          subFabVisibility = !plantDetailState.value.subFabVisibility
        )
      }
      is PlantDetailEvent.EnterName -> {
        _plantDetailState.value = plantDetailState.value.copy(
          plant = plantDetailState.value.plant.copy(
            name = event.value
          )
        )
      }
      is PlantDetailEvent.EnterDescription -> {
        _plantDetailState.value = plantDetailState.value.copy(
          plant = plantDetailState.value.plant.copy(
            description = event.value
          )
        )
      }
      is PlantDetailEvent.ChangeImageUri -> {
        _plantDetailState.value = plantDetailState.value.copy(
          imageUri = event.uri
        )
      }
      is PlantDetailEvent.SavePlant -> {
        if (dataState.value != DataState.Loading) {
          if (plantDetailState.value.plant.id == "") {
            addPlant()
          } else {
            updatePlant()
          }
        }
      }
      is PlantDetailEvent.DeletePlant -> {
        if (dataState.value != DataState.Loading) {
          deletePlant()
        }
      }
    }
  }

  private fun addPlant() {
    viewModelScope.launch {
      plantUseCases.addPlant(
        plantDetailState.value.plant,
        uri = plantDetailState.value.imageUri,
      ).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _plantDetailState.value = plantDetailState.value.copy(
            plant = it.data!!,
          )
        }
        if (it is DataState.Error) {
          _eventFLow.emit(PlantDetailUiEvent.ShowError(it.message))
        }
      }
    }
  }

  private fun updatePlant() {
    viewModelScope.launch {
      plantUseCases.updatePlant(
        plantDetailState.value.plant,
        uri = plantDetailState.value.imageUri
      ).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _plantDetailState.value = plantDetailState.value.copy(
            plant = it.data!!,
            subFabVisibility = false
          )
        }
        if (it is DataState.Error) {
          _eventFLow.emit(PlantDetailUiEvent.ShowError(it.message))
          _plantDetailState.value = plantDetailState.value.copy(
            subFabVisibility = false
          )
        }
      }
    }
  }

  private fun deletePlant() {
    val id = plantDetailState.value.plant.id
    if (!id.isNullOrEmpty() || !id.isNullOrBlank()) {
      viewModelScope.launch {
        plantUseCases.deletePlant(id = id)
          .collectLatest {
            if (it is DataState.Success) {
              _eventFLow.emit(PlantDetailUiEvent.NavigateBack)
            }
            if (it is DataState.Error) {
              _eventFLow.emit(PlantDetailUiEvent.ShowError(it.message))
            }
          }
      }
    }
  }
}
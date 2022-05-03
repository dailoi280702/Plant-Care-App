package com.example.plantcare.presentation.plants

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import com.example.plantcare.domain.utils.PlantOrder
import com.example.plantcare.presentation.plants.components.PlantsState
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsListViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases
) : ViewModel() {

  private val _state = mutableStateOf(PlantsState())
  val state: State<PlantsState> = _state

  private val _imageRef = mutableStateOf<StorageReference?>(null)
  val imageRef: State<StorageReference?> = _imageRef

  init {
    getPlants(state.value.plantsOrder)
  }

  private fun getPlants(plantOrder: PlantOrder? = null) {
    viewModelScope.launch {
      try {
        plantUseCases.getPlants(plantOrder = plantOrder).collect {
          if (it is DataState.Success) {
            _state.value = state.value.copy(
              plants = it.data,
              plantsOrder = plantOrder?:state.value.plantsOrder
            )
          }
        }
      } catch (e: Exception) {
        Log.d("error_check", e.message ?: "unknown error!")
      }
    }
  }


  fun onEvent(event: PlantsScreenEvent) {
    when (event) {
      is PlantsScreenEvent.ToggleOrderSection -> {
        _state.value = state.value.copy(
          isOrderSessionVisible = !state.value.isOrderSessionVisible
        )
      }
      is PlantsScreenEvent.Order -> {
        if (state.value.plantsOrder::class == event.plantOrder::class && state.value.plantsOrder.orderType == event.plantOrder.orderType) {
          return
        }
        getPlants(plantOrder = event.plantOrder)
      }
    }
  }
}
package com.example.plantcare.presentation.plants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import com.example.plantcare.presentation.plants.components.PlantsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantsListViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases
) : ViewModel() {

  private val _state = mutableStateOf(PlantsState())
  val state: State<PlantsState> = _state

  private fun toggleOrderSession() {
    _state.value = state.value.copy(
      isOrderSessionVisible = !state.value.isOrderSessionVisible
    )
  }

  fun onEvent(event: PlantsScreenEvent) {
    when (event) {
      is PlantsScreenEvent.ToggleOrderSection -> {
        toggleOrderSession()
      }
      is PlantsScreenEvent.Order -> {
        if (state.value.plantsOrder::class == event.plantOrder::class && state.value.plantsOrder.orderType == event.plantOrder.orderType) {
          return
        }
        // get plant by order
        _state.value = state.value.copy(
          plantsOrder = event.plantOrder
        )
      }
    }
  }
}
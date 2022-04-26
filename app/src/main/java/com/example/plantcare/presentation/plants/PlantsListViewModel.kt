package com.example.plantcare.presentation.plants

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import com.example.plantcare.presentation.plants.components.PlantsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsListViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases
) : ViewModel() {

  private val _state = mutableStateOf(PlantsState())
  val state: State<PlantsState> = _state

  init {
    getPlants()
  }

  private fun toggleOrderSession() {
    _state.value = state.value.copy(
      isOrderSessionVisible = !state.value.isOrderSessionVisible
    )
  }

  private fun getPlants() {
     viewModelScope.launch {
       try {
         plantUseCases.getPlants().collect{
           if (it is DataState.Success) {
             _state.value = state.value.copy(
               plants = it.data
             )
             Log.d("logggg", "${it.data}")
           }
         }
         Log.d("logggg", "${_state.value.plants}")
       } catch (e: Exception) {
         Log.d("logggg", e.message?: "????")
       }
     }
  }

  fun testAddPlant() {
    viewModelScope.launch {
      val plant: Plant = Plant(
        name = "test plant",
        description = "test plant description",
        dateAdded = System.currentTimeMillis(),
        imageURL = "https://images.pexels.com/photos/970089/pexels-photo-970089.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
      )
      Log.d("logggg", "add")
      plantUseCases.addPlant(plant = plant).collect{

      }
    }
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
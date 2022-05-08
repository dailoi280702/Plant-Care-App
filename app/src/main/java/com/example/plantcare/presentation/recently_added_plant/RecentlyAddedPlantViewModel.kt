package com.example.plantcare.presentation.recently_added_plant

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.PlantOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyAddedPlantViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases
) : ViewModel() {

  private val _plants = mutableStateOf<List<Plant>>(emptyList())
  val plants: State<List<Plant>> = _plants

  private val _dataState = mutableStateOf<DataState<List<Plant>?>>(DataState.Success(null))
  val dataState: State<DataState<List<Plant>?>> = _dataState

  init {
    viewModelScope.launch {
      try{
        plantUseCases.getPlants(
          plantOrder = PlantOrder.DateAdded(orderType = OrderType.Descending),
          limit = 10
        ).collect {
          _dataState.value = it
          if (it is DataState.Success) {
            _plants.value = it.data
          }
        }
      } catch (e: Exception) {
      }
    }
  }
}
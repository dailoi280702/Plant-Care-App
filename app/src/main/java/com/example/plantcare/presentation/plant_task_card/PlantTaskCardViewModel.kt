package com.example.plantcare.presentation.plant_task_card

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantTaskCardViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases
): ViewModel() {

  private val _plantName = mutableStateOf<String?>("")
  val plantName: State<String?> = _plantName

  fun getPlantName(plantId: String) {
    viewModelScope.launch {
      plantUseCases.getPlantName(id = plantId).collect {
        _plantName.value = it
      }
    }
  }
}
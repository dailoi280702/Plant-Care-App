package com.example.plantcare.presentation.add_edit_plant

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.plantcare.domain.use_case.plant.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
  private val plantUseCases: PlantUseCases,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {
  val id = mutableStateOf("")

  init {
    id.value = savedStateHandle.get<String>("plantId")?: "err no plant id"
    Log.d( "test_vm", id.value)
  }


}
package com.example.plantcare.presentation.add_edit_plant

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.presentation.main.MainViewModel

@Composable
fun AddEditPlantScreen(
  navController: NavController,
  viewModel: AddEditPlantViewModel = hiltViewModel(),
  mainViewModel: MainViewModel
) {
  Text(text = "add edit plant screen ${viewModel.id.value}")
  mainViewModel.setFloatingActionButton(icon = R.drawable.ic_save, contentDescription = "save icon") {
    Log.d("test_vm", "haha")
    viewModel.id.value = "aldkfjma"
  }
}

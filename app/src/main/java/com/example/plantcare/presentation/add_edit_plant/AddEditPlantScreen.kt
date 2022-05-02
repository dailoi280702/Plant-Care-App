package com.example.plantcare.presentation.add_edit_plant

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.R
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.presentation.add_edit_plant.components.ExpandableSurface
import com.example.plantcare.presentation.add_edit_plant.components.ImageSection
import com.example.plantcare.presentation.add_edit_plant.components.InfoSection
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.ui.theme.utils.customColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditPlantScreen(
  navController: NavController,
  viewModel: AddEditPlantViewModel = hiltViewModel(),
  mainViewModel: MainViewModel
) {

  val addEditPlantState = viewModel.addEditPlantState.value
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  val urlPainter = rememberAsyncImagePainter(model = addEditPlantState.plant.imageURL)
  val uriPainter = rememberAsyncImagePainter(model = addEditPlantState.imageUri)
  val showLocalImage = addEditPlantState.plant.id == "" && addEditPlantState.imageUri == null
  val painter = if (addEditPlantState.imageUri != null) uriPainter else urlPainter
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) {
    viewModel.onEvent(
      AddEditPlantEvent.ChangeImageUri(it)
    )
  }

  mainViewModel.setFloatingActionButton(
    icon = R.drawable.ic_save,
    contentDescription = "save icon"
  ) {
    viewModel.onEvent(AddEditPlantEvent.SavePlant)
  }

  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest { event ->
      when (event) {
        is AddEditPlantUiEvent.ShowError -> {
          scope.launch {
            snackbarHostState.showSnackbar(
              message = event.message,
              duration = SnackbarDuration.Short
            )
          }
        }
      }
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    ExpandableSurface(
      title = "Plant information",
      expanded = addEditPlantState.expandedInfo,
      onClick = { viewModel.onEvent(AddEditPlantEvent.ToggleInfoSection) }
    ) {
      InfoSection(
        name = addEditPlantState.plant.name ?: "",
        description = addEditPlantState.plant.description ?: "",
        onNameChange = {
          viewModel.onEvent(AddEditPlantEvent.EnterName(it))
        },
        onDescriptionChange = {
          viewModel.onEvent(AddEditPlantEvent.EnterDescription(it))
        }
      )
    }

    Divider()
    ExpandableSurface(
      title = "Image",
      expanded = addEditPlantState.expandedImage,
      onClick = { viewModel.onEvent(AddEditPlantEvent.ToggleImageSection) }
    ) {
      ImageSection(
        showLocalImage = showLocalImage,
        painter = painter,
      ) {
        launcher.launch("image/*")
      }
    }

    if (addEditPlantState.plant.id != "") {
      Divider()
      ExpandableSurface(
        title = "Todo",
        expanded = addEditPlantState.expandedTasks,
        onClick = { viewModel.onEvent(AddEditPlantEvent.ToggleTasksSection) }
      ) {
        Text(text = addEditPlantState.plant.id!!)
      }
    }

    Spacer(modifier = Modifier.height(160.dp))
  }

  if (viewModel.dataState.value is DataState.Loading) {
    Surface(
      modifier = Modifier
        .fillMaxSize(),
      color = Color.Transparent
    ) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator()
      }
    }
  }

  SnackbarHost(
    hostState = snackbarHostState,
    snackbar = { snackbarData: SnackbarData ->
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(),
          elevation = 8.dp
        ) {
          Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = snackbarData.message,
              color = MaterialTheme.customColors.error
            )
          }
        }
      }
    }
  )
}


package com.example.plantcare.presentation.add_edit_plant

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.R
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.presentation.add_edit_plant.components.*
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialog
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.plantTaskList.components.PlantTaskList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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
  val fabIcon = if (addEditPlantState.plant.id == "") R.drawable.ic_save else R.drawable.add
  val rotationState by animateFloatAsState(
    targetValue = if (addEditPlantState.subFabVisibility) 45f else 0f
  )
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) {
    viewModel.onEvent(
      AddEditPlantEvent.ChangeImageUri(it)
    )
  }

  mainViewModel.setFloatingActionButton(
    icon = fabIcon,
    rotation = rotationState,
    contentDescription = "save icon"
  ) {
    if (addEditPlantState.plant.id == "")
      viewModel.onEvent(AddEditPlantEvent.SavePlant)
    else
      viewModel.onEvent(AddEditPlantEvent.ToggleSubFab)
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
        is AddEditPlantUiEvent.NavigateBack -> {
          navController.navigateUp()
        }
      }
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
//      .verticalScroll(rememberScrollState())
  ) {
    ExpandableSurface(
      title = "Plant information",
      expanded = addEditPlantState.expandedInfo,
      onClick = { viewModel.onEvent(AddEditPlantEvent.ToggleInfoSection) }
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f),
          contentAlignment = Alignment.Center
        ) {
          ImageSection(
            showLocalImage = showLocalImage,
            painter = painter,
          ) {
            launcher.launch("image/*")
          }
        }
        Box(modifier = Modifier.weight(1f)) {
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
      }
    }

    Divider()
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
      Text(
        text = "Todo",
        maxLines = 1,
        fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface
      )
      TextButton(onClick = {
        viewModel.onEvent(AddEditPlantEvent.UpdateCurrentPlantTask(null))
        viewModel.onEvent(AddEditPlantEvent.ToggleTaskDialog)
      }) {
        Text(text = "add todo")
      }
    }

    PlantTaskList(navController = navController, plantId = addEditPlantState.plant.id!!) {
      viewModel.onEvent(AddEditPlantEvent.UpdateCurrentPlantTask(it))
      viewModel.onEvent(AddEditPlantEvent.ToggleTaskDialog)
    }
  }

  AnimatedVisibility(
    visible = addEditPlantState.subFabVisibility,
    enter = fadeIn(),
    exit = fadeOut()
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize(),
      color = Color.Black.copy(alpha = 0.5f),
      onClick = {
        viewModel.onEvent(AddEditPlantEvent.ToggleSubFab)
      }
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
      ) {
        HiddenFloatingActionButton(
          visible = addEditPlantState.subFabVisibility,
          text = "Delete",
          containerColor = MaterialTheme.colorScheme.errorContainer,
          onContainerColor = MaterialTheme.colorScheme.onErrorContainer,
          painter = painterResource(id = R.drawable.ic_delete)
        ) {
          viewModel.onEvent(AddEditPlantEvent.DeletePlant)
        }
        Spacer(modifier = Modifier.height(4.dp))
        HiddenFloatingActionButton(
          visible = addEditPlantState.subFabVisibility,
          text = "Save",
          containerColor = MaterialTheme.colorScheme.tertiaryContainer,
          onContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
          painter = painterResource(id = R.drawable.ic_save)
        ) {
          viewModel.onEvent(AddEditPlantEvent.SavePlant)
        }
        Spacer(modifier = Modifier.height(88.dp))
      }
    }
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

  ErrorSnackbarHost(snackbarHostState = snackbarHostState)
  val idx = remember {
    mutableStateOf(0)
  }

  AddEditTaskDialog(
    openDiaLog = addEditPlantState.taskDialogVisibility,
    plantId = addEditPlantState.plant.id,
    plantTask = viewModel.currentPlantTask.value
  ) {
//    viewModel.onEvent(AddEditPlantEvent.UpdateCurrentPlantTask(null))
    viewModel.onEvent(AddEditPlantEvent.ToggleTaskDialog)
  }
}


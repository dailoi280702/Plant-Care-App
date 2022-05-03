package com.example.plantcare.presentation.add_edit_plant

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.R
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.presentation.add_edit_plant.components.*
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.ui.theme.utils.customColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
          containerColor = MaterialTheme.customColors.errorContainer,
          onContainerColor = MaterialTheme.customColors.onErrorContainer,
          painter = painterResource(id = R.drawable.ic_delete)
        ) {
          viewModel.onEvent(AddEditPlantEvent.DeletePlant)
        }
        Spacer(modifier = Modifier.height(4.dp))
        HiddenFloatingActionButton(
          visible = addEditPlantState.subFabVisibility,
          text = "Save",
          containerColor = MaterialTheme.customColors.tertiaryContainer,
          onContainerColor = MaterialTheme.customColors.onTertiaryContainer,
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
}


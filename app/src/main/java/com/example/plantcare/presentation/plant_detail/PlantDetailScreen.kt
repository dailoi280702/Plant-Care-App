package com.example.plantcare.presentation.plant_detail

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.R
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialog
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialogEvent
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialogViewModel
import com.example.plantcare.presentation.plant_detail.components.*
import com.example.plantcare.presentation.plant_detail_todos.components.PlantTaskList
import com.example.plantcare.presentation.todo.TodoEvent
import com.example.plantcare.presentation.todo.TodoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlantScreen(
  navController: NavController,
  viewModel: PlantDetailViewModel = hiltViewModel(),
  todoDialogViewModel: AddEditTaskDialogViewModel = hiltViewModel(),
  todoViewModel: TodoViewModel = hiltViewModel(),
) {
  
  val plantDetailState = viewModel.plantDetailState.value
  val scope = rememberCoroutineScope()
  val errorSnackbarHostState = remember { SnackbarHostState() }
  val snackbarHostState = remember { SnackbarHostState() }
  val urlPainter = rememberAsyncImagePainter(model = plantDetailState.plant.imageURL)
  val uriPainter = rememberAsyncImagePainter(model = plantDetailState.imageUri)
  val showLocalImage = plantDetailState.plant.id == "" && plantDetailState.imageUri == null
  val painter = if (plantDetailState.imageUri != null) uriPainter else urlPainter
  val fabIcon = if (plantDetailState.plant.id == "") R.drawable.ic_save else R.drawable.add
  val rotationState by animateFloatAsState(
    targetValue = if (plantDetailState.subFabVisibility) 45f else 0f
  )
  val dividerPadding =
    animateDpAsState(targetValue = if (viewModel.plantDetailState.value.expandedInfo) 16.dp else 0.dp)
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) {
    viewModel.onEvent(
      PlantDetailEvent.ChangeImageUri(it)
    )
  }
  
  LaunchedEffect(key1 = true) {
    todoViewModel.eventFlow.collectLatest { event ->
      if (event) {
        scope.launch {
          val result = snackbarHostState.showSnackbar(
            message = "A todo has been deleted",
            withDismissAction = true,
            duration = SnackbarDuration.Short,
            actionLabel = "UNDO"
          )
          if (result == SnackbarResult.ActionPerformed) {
            todoViewModel.onEvent(TodoEvent.RestoreTodo)
          }
        }
      }
    }
  }
  
  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest { event ->
      when (event) {
        is PlantDetailUiEvent.ShowError -> {
          scope.launch {
            errorSnackbarHostState.showSnackbar(
              message = event.message,
              duration = SnackbarDuration.Short
            )
          }
        }
        is PlantDetailUiEvent.NavigateBack -> {
          navController.navigateUp()
        }
      }
    }
  }
  
  Scaffold(
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState) {
        Snackbar(
          snackbarData = it,
          containerColor = MaterialTheme.colorScheme.surfaceVariant,
          contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
          actionColor = MaterialTheme.colorScheme.primary,
          dismissActionContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          if (plantDetailState.plant.id == "")
            viewModel.onEvent(PlantDetailEvent.SavePlant)
          else
            viewModel.onEvent(PlantDetailEvent.ToggleSubFab)
        },
        elevation = FloatingActionButtonDefaults.elevation()
      ) {
        Icon(
          painter = painterResource(id = fabIcon),
          contentDescription = null,
          modifier = Modifier
            .size(24.dp)
            .rotate(rotationState)
        )
      }
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      ExpandableSurface(
        title = "Plant information",
        expanded = plantDetailState.expandedInfo,
        onClick = { viewModel.onEvent(PlantDetailEvent.ToggleInfoSection) }
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
              name = plantDetailState.plant.name ?: "",
              description = plantDetailState.plant.description ?: "",
              onNameChange = {
                viewModel.onEvent(PlantDetailEvent.EnterName(it))
              },
              onDescriptionChange = {
                viewModel.onEvent(PlantDetailEvent.EnterDescription(it))
              }
            )
          }
        }
      }
      
      if (plantDetailState.expandedTasks) {
        Divider(modifier = Modifier.padding(horizontal = dividerPadding.value))
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
            todoDialogViewModel.init(plant = plantDetailState.plant)
            todoDialogViewModel.onEvent(AddEditTaskDialogEvent.UpdateDialogVisibility(true))
          }) {
            Text(text = "add todo")
          }
        }
        
        PlantTaskList(
          navController = navController,
          plantId = plantDetailState.plant.id!!,
          todoViewModel = todoViewModel
        ) {
          todoDialogViewModel.init(plant = plantDetailState.plant, todo = it)
          todoDialogViewModel.onEvent(AddEditTaskDialogEvent.UpdateDialogVisibility(true))
        }
      }
    }
  }
  
  AnimatedVisibility(
    visible = plantDetailState.subFabVisibility,
    enter = fadeIn(),
    exit = fadeOut()
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize(),
      color = Color.Black.copy(alpha = 0.5f),
      onClick = {
        viewModel.onEvent(PlantDetailEvent.ToggleSubFab)
      }
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
      ) {
        HiddenFloatingActionButton(
          visible = plantDetailState.subFabVisibility,
          text = "Delete",
          containerColor = MaterialTheme.colorScheme.errorContainer,
          onContainerColor = MaterialTheme.colorScheme.onErrorContainer,
          painter = painterResource(id = R.drawable.ic_delete)
        ) {
          viewModel.onEvent(PlantDetailEvent.ToggleSubFab)
          viewModel.onEvent(PlantDetailEvent.ToggleConfirmDetetionDialog)
        }
        Spacer(modifier = Modifier.height(4.dp))
        HiddenFloatingActionButton(
          visible = plantDetailState.subFabVisibility,
          text = "Save",
          containerColor = MaterialTheme.colorScheme.tertiaryContainer,
          onContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
          painter = painterResource(id = R.drawable.ic_save)
        ) {
          viewModel.onEvent(PlantDetailEvent.SavePlant)
        }
        Spacer(modifier = Modifier.height(88.dp))
      }
    }
  }
  
  if (plantDetailState.confirmDeletionDialogVisibility) {
    AlertDialog(
      onDismissRequest = {
        viewModel.onEvent(PlantDetailEvent.ToggleConfirmDetetionDialog)
      },
      title = {
        Text(text = "Confirm deletetion")
      },
      dismissButton = {
        TextButton(onClick = {
          viewModel.onEvent(PlantDetailEvent.ToggleConfirmDetetionDialog)
        }) {
          Text(text = "Cancel")
        }
      },
      confirmButton = {
        TextButton(
          onClick = {
            viewModel.onEvent(PlantDetailEvent.ToggleSubFab)
            viewModel.onEvent(PlantDetailEvent.DeletePlant)
          }
        ) {
          Text(text = "Delete anyway", color = MaterialTheme.colorScheme.error)
        }
      },
      text = {
        Text(text = "Warning! this action can not be undo, todos and images related to this plant will also be deleted!")
      },
      icon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_delete),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.error,
          modifier = Modifier.size(24.dp)
        )
      }
    )
  }
  
  if (viewModel.dataState.value is DataState.Loading) {
    Dialog(onDismissRequest = {}) {
      CircularProgressIndicator()
    }
  }
  
  ErrorSnackbarHost(snackbarHostState = errorSnackbarHostState)
  
  AddEditTaskDialog(viewModel = todoDialogViewModel)
  
}


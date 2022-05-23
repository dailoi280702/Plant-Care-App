package com.example.plantcare.presentation.plantTaskList.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.presentation.plantTaskList.PlantTaskListEvent
import com.example.plantcare.presentation.plantTaskList.PlantTaskListViewModel
import com.example.plantcare.presentation.plant_task_card.components.PlantTaskCard
import com.example.plantcare.presentation.plant_task_card.components.SwipeablePlantTaskContainer

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun PlantTaskList(
  navController: NavController,
  plantId: String,
  viewModel: PlantTaskListViewModel = hiltViewModel(),
  onEditClick: (PlantTask) -> Unit
) {

  LaunchedEffect(Unit) {
    viewModel.init(plantId)
  }

  val plantTask = viewModel.plantTaskListState.value.taskList
  val expandedCard = viewModel.plantTaskListState.value.expandedPlantTaskCard

  LazyColumn(
    modifier = Modifier
      .fillMaxWidth(),
    contentPadding = PaddingValues(bottom = 160.dp)
  ) {
    items(items = plantTask, key = { it.taskId!! }) { task ->
      Box(
        modifier = Modifier.animateItemPlacement(
          spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
          )
        )
      ) {
        if (task != viewModel.removedTask.value) {
          val dismissState = rememberDismissState(initialValue = DismissValue.Default) { dismissValue ->
            if (dismissValue == DismissValue.DismissedToEnd) {
              viewModel.onEvent(PlantTaskListEvent.DeletePlantTask(task))
            }
            true
          }

          SwipeablePlantTaskContainer(
            dismissState = dismissState
          ) {
            PlantTaskCard(
              dismissState = dismissState,
              task = task,
              onPlantNameClick = {},
              onEditClick = {onEditClick(task)},
              onDone = { viewModel.onEvent(PlantTaskListEvent.MarkAsDone(task)) },
              expanded = expandedCard == task
            ) {
              if (expandedCard == task) {
                viewModel.onEvent(PlantTaskListEvent.UpdateExpandedCard(null))
              } else {
                viewModel.onEvent(PlantTaskListEvent.UpdateExpandedCard(task))
              }
            }
          }
        }
      }
    }
  }
}
package com.example.plantcare.presentation.plantTaskList.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.presentation.plantTaskList.PlantTaskListEvent
import com.example.plantcare.presentation.plantTaskList.PlantTaskListViewModel
import com.example.plantcare.presentation.plant_task_card.components.PlantTaskCard
import com.example.plantcare.presentation.plant_task_card.components.SwipeablePlantTaskContainer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantTaskList(
  navController: NavController,
  plantId: String,
  viewModel: PlantTaskListViewModel = hiltViewModel()
) {

  LaunchedEffect(Unit) {
    viewModel.init(plantId)
  }

  val plantTask = viewModel.plantTaskListState.value.taskList
  val expandedCard = viewModel.plantTaskListState.value.expandedPlantTaskCard

  LazyColumn(
    modifier = Modifier
      .fillMaxWidth(),
//    contentPadding = PaddingValues(bottom = 160.dp)
  ) {
    items(items = plantTask, key = { it.taskId!! }) {
      Box(
        modifier = Modifier.animateItemPlacement(
          spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
          )
        )
      ) {
        if (it != viewModel.removedTask.value) {
          SwipeablePlantTaskContainer(
            onDelete = { viewModel.onEvent(PlantTaskListEvent.DeletePlantTask(it)) }
          ) {
            PlantTaskCard(
              task = it,
              onPlantNameClick = {},
              onDone = { viewModel.onEvent(PlantTaskListEvent.MarkAsDone(it)) },
              expanded = expandedCard == it
            ) {
              if (expandedCard == it) {
                viewModel.onEvent(PlantTaskListEvent.UpdateExpandedCard(null))
              } else {
                viewModel.onEvent(PlantTaskListEvent.UpdateExpandedCard(it))
              }
            }
          }
        }
      }
    }
  }
}
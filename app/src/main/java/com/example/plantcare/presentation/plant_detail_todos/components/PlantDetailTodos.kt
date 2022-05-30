package com.example.plantcare.presentation.plant_detail_todos.components

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
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.presentation.plant_detail_todos.PlantDetailTodosViewModel
import com.example.plantcare.presentation.todo_card.components.PlantTaskCard
import com.example.plantcare.presentation.todo_card.components.SwipeablePlantTaskContainer
import com.example.plantcare.presentation.todo.TodoEvent
import com.example.plantcare.presentation.todo.TodoViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun PlantTaskList(
  navController: NavController,
  plantId: String,
  viewModel: PlantDetailTodosViewModel = hiltViewModel(),
  todoViewModel: TodoViewModel,
  onEditClick: (Todo) -> Unit
) {
  
  LaunchedEffect(Unit) {
    viewModel.init(plantId)
  }
  
  val todos = viewModel.plantDetailTodosState.value.taskList
  val expandedCard = todoViewModel.todoState.value.expandedCard
  
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth(),
    contentPadding = PaddingValues(bottom = 160.dp)
  ) {
    items(items = todos, key = { it.todoId!! }) { todo ->
      Box(
        modifier = Modifier.animateItemPlacement(
          spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
          )
        )
      ) {
        val dismissState =
          rememberDismissState(initialValue = DismissValue.Default) { dismissValue ->
            if (dismissValue == DismissValue.DismissedToEnd) {
              todoViewModel.onEvent(TodoEvent.DeleteTodo(todo))
            }
            true
          }
        
        SwipeablePlantTaskContainer(
          dismissState = dismissState
        ) {
          PlantTaskCard(
            dismissState = dismissState,
            task = todo,
            onPlantNameClick = {},
            onEditClick = { onEditClick(todo) },
            onDone = { todoViewModel.onEvent(TodoEvent.MarkTodoAsDone(todo)) },
            expanded = expandedCard == todo
          ) {
            todoViewModel.onEvent(TodoEvent.UpdateExpandedCard(todo))
          }
        }
      }
    }
  }
}
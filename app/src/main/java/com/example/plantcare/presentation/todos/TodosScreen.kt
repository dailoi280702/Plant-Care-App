package com.example.plantcare.presentation.todos


import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.todo_card.components.PlantTaskCard
import com.example.plantcare.presentation.todo_card.components.SwipeablePlantTaskContainer
import com.example.plantcare.presentation.todo.TodoEvent
import com.example.plantcare.presentation.todo.TodoViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
  ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
  ExperimentalMaterialApi::class, ExperimentalAnimationApi::class
)
@Composable
fun TasksScreen(
  navController: NavController,
  todoViewModel: TodoViewModel = hiltViewModel(),
  viewModel: TodosViewModel = hiltViewModel(),
  bottomBar: @Composable () -> Unit
) {
  
  val state = viewModel.todosState.value
  val todoTimes = listOf(TodoTime.Today, TodoTime.UpComing, TodoTime.Overdue)
  val todoOrder = listOf(
    TodoOrder.Default(state.todoOrder.orderType),
    TodoOrder.Title(state.todoOrder.orderType),
    TodoOrder.DueDate(state.todoOrder.orderType),
    TodoOrder.Importance(state.todoOrder.orderType)
  )
  val orderType = listOf(OrderType.Ascending, OrderType.Descending)
  val filterSectionVisibility = state.filterSectionVisibility
  val rotation by animateFloatAsState(targetValue = if (filterSectionVisibility) 180f else 0f)
  val filterSectionHeight by animateDpAsState(targetValue = if (filterSectionVisibility) 96.dp else 0.dp)
  var showTodoOrder by remember { mutableStateOf(false) }
  var showOrderType by remember { mutableStateOf(false) }
  val isNotDefaultFilter = state.todoOrder::class.java != TodoOrder.Default::class.java
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  
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
  
  Scaffold(
    topBar = {
      Column {
        SmallTopAppBar(
          title = {
            Text(
              text = "Todos",
              color = MaterialTheme.colorScheme.onSurface,
              style = MaterialTheme.typography.titleLarge
            )
          },
          actions = {
            IconButton(onClick = { viewModel.onEvent(TodosEvent.ToggleFilterSection) }) {
              Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.rotate(rotation)
              )
            }
          }
        )
        AnimatedVisibility(
          visible = filterSectionVisibility,
          enter = fadeIn() + slideInVertically(),
          exit = fadeOut() + slideOutVertically() + scaleOut()
        ) {
          Column {
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ) {
              items(todoTimes) {
                FilterChip(
                  selected = state.todoTimes.contains(it),
                  onClick = {
                    viewModel.onEvent(TodosEvent.UpdateTodoTimes(it))
                  },
                  label = { Text(it.text) },
                  selectedIcon = {
                    Icon(
                      imageVector = Icons.Filled.Done,
                      contentDescription = "Localized Description",
                      modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                  }
                )
              }
            }
            
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ) {
              item {
                Box {
                  FilterChip(
                    selected = isNotDefaultFilter,
                    onClick = { showTodoOrder = !showTodoOrder },
                    label = { Text(state.todoOrder.text) },
                    trailingIcon = {
                      Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  )
                  DropdownMenu(
                    expanded = showTodoOrder,
                    onDismissRequest = { showTodoOrder = !showTodoOrder }
                  ) {
                    todoOrder.onEach {
                      DropdownMenuItem(
                        text = { Text(text = it.text) },
                        onClick = {
                          viewModel.onEvent(TodosEvent.UpdateTodoOrder(it))
                          showTodoOrder = !showTodoOrder
                        }
                      )
                    }
                  }
                }
              }
              item {
                Box {
                  FilterChip(
                    selected = isNotDefaultFilter,
                    onClick = { showOrderType = !showOrderType && isNotDefaultFilter },
                    label = { Text(text = if (isNotDefaultFilter) state.todoOrder.orderType.text else "Order direction") },
                    trailingIcon = {
                      if (isNotDefaultFilter) {
                        Icon(
                          imageVector = Icons.Default.ArrowDropDown,
                          contentDescription = null,
                          tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                      }
                    },
                    enabled = isNotDefaultFilter
                  )
                  DropdownMenu(
                    expanded = showOrderType,
                    onDismissRequest = { showOrderType = !showOrderType }
                  ) {
                    orderType.onEach {
                      DropdownMenuItem(
                        text = { Text(text = it.text) },
                        onClick = {
                          viewModel.onEvent(
                            TodosEvent.UpdateTodoOrder(
                              state.todoOrder.copy(
                                it
                              )
                            )
                          )
                          showOrderType = !showOrderType
                        }
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    },
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
    bottomBar = bottomBar
  ) {
    SwipeRefresh(
      state = rememberSwipeRefreshState(isRefreshing = true),
      onRefresh = { viewModel.onEvent(TodosEvent.RefreshTodos) },
      indicator = { state, refreshTrigger ->
        SwipeRefreshIndicator(
          state = state,
          refreshTriggerDistance = refreshTrigger,
          scale = true,
          backgroundColor = MaterialTheme.colorScheme.primary
        )
      }
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth(),
        contentPadding = PaddingValues(top = 72.dp + filterSectionHeight, bottom = 160.dp)
      ) {
        items(items = state.todos, key = { it.todoId!! }) { todo ->
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
                onPlantNameClick = {
                  navController.navigate(Screens.AddPlantScreen.route + "?plantId=${todo.plantId}") {
                    navController.graph.startDestinationRoute?.let { route ->
                      popUpTo(route) {
                        saveState = true
                      }
                    }
                    launchSingleTop = true
                    restoreState = true
                  }
                },
                onEditClick = {
                },
                onDone = { todoViewModel.onEvent(TodoEvent.MarkTodoAsDone(todo)) },
                expanded = todo == todoViewModel.todoState.value.expandedCard
              ) {
                todoViewModel.onEvent(TodoEvent.UpdateExpandedCard(todo))
              }
            }
          }
        }
      }
    }
  }
}
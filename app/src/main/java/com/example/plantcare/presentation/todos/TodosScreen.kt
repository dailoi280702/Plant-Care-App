package com.example.plantcare.presentation.todos


import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.data.utils.DataState.Loading
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialog
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialogEvent
import com.example.plantcare.presentation.add_edit_task.AddEditTaskDialogViewModel
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.todo.TodoEvent
import com.example.plantcare.presentation.todo.TodoViewModel
import com.example.plantcare.presentation.todo_card.components.PlantTaskCard
import com.example.plantcare.presentation.todo_card.components.SwipeablePlantTaskContainer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
  ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
  ExperimentalMaterialApi::class
)
@Composable
fun TasksScreen(
  navController: NavController,
  todoViewModel: TodoViewModel = hiltViewModel(),
  viewModel: TodosViewModel = hiltViewModel(),
  todoDialogViewModel: AddEditTaskDialogViewModel = hiltViewModel(),
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
  val filterSectionHeight by animateDpAsState(targetValue = if (filterSectionVisibility) 216.dp else 0.dp) {
  
  }
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
          },
          colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(
              alpha = 0.96f
            )
          )
        )
        AnimatedVisibility(
          visible = filterSectionVisibility,
          enter = fadeIn() + slideInVertically(),
          exit = fadeOut() + slideOutVertically()
        ) {
          Column(
            modifier = Modifier
              .background(
                Brush.verticalGradient(
                  colors = listOf(
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                  )
                )
              )
              .padding(horizontal = 16.dp)
          ) {
            Divider(
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                painter = painterResource(id = R.drawable.ic_filter_alt_black_48dp),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.surfaceTint
              )
              Text(
                text = "Filter by",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium
              )
            }
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier
                .fillMaxWidth()
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
                  },
                )
              }
            }
            
            Divider(
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.surfaceTint
              )
              Text(
                text = "Order by",
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.material.LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
              )
            }
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier
                .fillMaxWidth()
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
                    },
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
                    enabled = isNotDefaultFilter,
//                    colors = FilterChipDefaults.filterChipColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f))
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
            Divider(
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.padding(top = 8.dp)
            )
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
      state = rememberSwipeRefreshState(isRefreshing = viewModel.dataState.value is Loading),
      onRefresh = { viewModel.onEvent(TodosEvent.RefreshTodos) },
      indicator = { state, refreshTrigger ->
        SwipeRefreshIndicator(
          state = state,
          refreshTriggerDistance = refreshTrigger,
          scale = true,
          backgroundColor = MaterialTheme.colorScheme.primary,
          modifier = Modifier.padding(top = 72.dp + filterSectionHeight)
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
                  todoDialogViewModel.init(todo = todo)
                  todoDialogViewModel.onEvent(AddEditTaskDialogEvent.UpdateDialogVisibility(true))
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
  
  AddEditTaskDialog(viewModel = todoDialogViewModel)
}
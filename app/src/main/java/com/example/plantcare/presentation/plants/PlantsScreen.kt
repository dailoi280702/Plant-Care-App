package com.example.plantcare.presentation.plants

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.PlantOrder
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.plants.components.PlantCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlantsScreen(
  navController: NavController,
  scaffoldState: ScaffoldState,
  viewModel: PlantsListViewModel = hiltViewModel(),
  bottomBar: @Composable () -> Unit
) {
  
  val state = viewModel.state.value
  val configuration = LocalConfiguration.current
  val gridCellsColumn =
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
  val scrollState = rememberLazyGridState()
  val position by animateDpAsState(if (viewModel.scrollUp.value) (-64).dp else 0.dp)
  
  viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
  
  Scaffold(
    topBar = {
      SmallTopAppBar(
//        colors = TopAppBarDefaults.smallTopAppBarColors(
//          containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
//        ),
        modifier = Modifier
          .graphicsLayer { translationY = position.toPx() },
        title = {
          Text(
            text = "Your plants",
            style = MaterialTheme.typography.titleLarge,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
          )
        },
        actions = {
          Box {
            IconButton(
              onClick = {
                viewModel.onEvent(PlantsScreenEvent.ToggleOrderSection)
              }
            ) {
              Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = null,
                Modifier.size(24.dp)
              )
            }
            DropdownMenu(
              expanded = state.isOrderSessionVisible,
              onDismissRequest = {
                viewModel.onEvent(
                  PlantsScreenEvent.ToggleOrderSection
                )
              },
              modifier = Modifier.defaultMinSize(minWidth = 122.dp)
            ) {
              DropdownMenuItem(
                text = { Text(text = "Name A-Z") },
                onClick = { viewModel.onEvent(PlantsScreenEvent.Order(PlantOrder.Name(OrderType.Ascending))) },
                trailingIcon = {
                  val selected =
                    state.plantsOrder is PlantOrder.Name && state.plantsOrder.orderType is OrderType.Ascending
                  if (selected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                  }
                }
              )
              DropdownMenuItem(
                onClick = {
                  viewModel.onEvent(PlantsScreenEvent.Order(PlantOrder.Name(OrderType.Descending)))
                },
                text = { Text(text = "Name Z-A") },
                trailingIcon = {
                  val selected =
                    state.plantsOrder is PlantOrder.Name && state.plantsOrder.orderType is OrderType.Descending
                  if (selected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                  }
                }
              )
              DropdownMenuItem(
                onClick = {
                  viewModel.onEvent(
                    PlantsScreenEvent.Order(
                      PlantOrder.DateAdded(
                        OrderType.Descending
                      )
                    )
                  )
                },
                text = { Text(text = "Date latest") },
                trailingIcon = {
                  val selected =
                    state.plantsOrder is PlantOrder.DateAdded && state.plantsOrder.orderType is OrderType.Descending
                  if (selected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                  }
                }
              )
              DropdownMenuItem(onClick = {
                viewModel.onEvent(
                  PlantsScreenEvent.Order(
                    PlantOrder.DateAdded(
                      OrderType.Ascending
                    )
                  )
                )
              },
                text = { Text(text = "Date oldest") },
                trailingIcon = {
                  val selected =
                    state.plantsOrder is PlantOrder.DateAdded && state.plantsOrder.orderType is OrderType.Ascending
                  if (selected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                  }
                }
              )
            }
          }
        }
      )
    },
    bottomBar = bottomBar,
    floatingActionButton = {
      FloatingActionButton(
        onClick = { navController.navigate(Screens.AddPlantScreen.route) },
        elevation = FloatingActionButtonDefaults.elevation()
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_edit_outline),
          contentDescription = "Icon edit",
          modifier = Modifier
            .size(24.dp)
        )
      }
    }
  ) {
    LazyVerticalGrid(
      columns = GridCells.Fixed(gridCellsColumn),
      modifier = Modifier.padding(horizontal = 8.dp),
      state = scrollState,
      contentPadding = PaddingValues(top = 72.dp, bottom = 160.dp)
    ) {
      items(viewModel.state.value.plants) { plant ->
        if (plant.id != null) {
          PlantCard(
            plant = plant,
            modifier = Modifier
              .fillMaxWidth()
              .padding(4.dp)
              .clickable {
                navController.navigate(Screens.AddPlantScreen.route + "?plantId=${plant.id}")
              },
          )
        }
      }
    }
  }
}
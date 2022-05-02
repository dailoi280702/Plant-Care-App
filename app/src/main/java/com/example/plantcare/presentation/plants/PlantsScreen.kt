package com.example.plantcare.presentation.plants

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.plants.components.PlantCard
import com.example.plantcare.presentation.plants.components.SearchAndFilterSession

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantsScreen(
  navController: NavController,
  scaffoldState: ScaffoldState,
  mainViewModel: MainViewModel,
  viewModel: PlantsListViewModel = hiltViewModel()
) {

  val context = LocalContext.current
  val state = viewModel.state.value
  mainViewModel.setFloatingActionButton(
    icon = R.drawable.ic_edit_outline,
    contentDescription = "add icon"
  ) {
    navController.navigate(Screens.AddPlantScreen.route)
  }
  val rotationState by animateFloatAsState(
    targetValue = if (state.isOrderSessionVisible) 180f else 0f
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = "Your plants",
        style = MaterialTheme.typography.h6
      )
      IconButton(
        onClick = {
          viewModel.onEvent(PlantsScreenEvent.ToggleOrderSection)
        },
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .rotate(rotationState),
      ) {
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Sort"
        )
      }
    }
    AnimatedVisibility(
      visible = state.isOrderSessionVisible,
      enter = fadeIn() + slideInVertically(),
      exit = fadeOut() + slideOutVertically()
    ) {
      SearchAndFilterSession(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        plantOrder = state.plantsOrder,
        onOrderChange = {
          viewModel.onEvent(PlantsScreenEvent.Order(it))
        }
      )
    }
    LazyVerticalGrid(
      cells = GridCells.Fixed(2),
      modifier = Modifier.padding(horizontal = 8.dp)
    ) {
      items(items = viewModel.state.value.plants) { plant ->
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
      item {
        Spacer(
          modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
        )
      }
      item {
        Spacer(
          modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
        )
      }
    }
  }
}
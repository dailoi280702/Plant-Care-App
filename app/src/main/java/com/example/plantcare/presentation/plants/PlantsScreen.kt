package com.example.plantcare.presentation.plants

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.presentation.main.MainViewModel
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
  val fab: @Composable () -> Unit = {
    ExtendedFloatingActionButton(
      text = { Text(text = "Add plant") },
      onClick = { /*TODO*/ },
      icon = {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add Plant"
        )
      }
    )
  }

  mainViewModel.setFloatingActionButton(fab)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        modifier = Modifier.padding(horizontal = 4.dp),
        text = "Your plants",
        style = MaterialTheme.typography.h5
      )
      IconButton(
        onClick = {
          viewModel.onEvent(PlantsScreenEvent.ToggleOrderSection)
        }
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
    Spacer(modifier = Modifier.height(8.dp))
    LazyVerticalGrid(
      cells = GridCells.Fixed(2),
      modifier = Modifier.padding(bottom = 50.dp)
    ) {
      items(10) {
        PlantCard(
          imageURL = "https://images.pexels.com/photos/8160274/pexels-photo-8160274.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260",
          name = "A plant",
          dateAdded = 1650787172087,
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .width(200.dp)
            .clickable {
              Toast
                .makeText(context, "card clicked", Toast.LENGTH_LONG)
                .show()
            }
        )
      }
    }
  }
}
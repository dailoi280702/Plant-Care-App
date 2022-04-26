package com.example.plantcare.presentation.plants

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.plantcare.ui.theme.utils.customColors

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
      modifier = Modifier
        .height(56.dp),
      text = {
        Text(
          text = "new plant",
          color = MaterialTheme.customColors.onPrimaryContainer,
          fontSize = MaterialTheme.typography.body1.fontSize
        )
      },
      onClick = {
        /*TODO*/
        viewModel.testAddPlant()
      },
      icon = {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "new plant",
          tint = MaterialTheme.customColors.onPrimaryContainer
        )
      },
      shape = RoundedCornerShape(16.dp),
      backgroundColor = MaterialTheme.customColors.primaryContainer
    )
  }

  mainViewModel.setFloatingActionButton(fab)

  Column(
    modifier = Modifier
      .fillMaxSize()
//      .padding(8.dp)
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
        style = MaterialTheme.typography.h5
      )
      IconButton(
        onClick = {
          viewModel.onEvent(PlantsScreenEvent.ToggleOrderSection)
        },
        modifier = Modifier.padding(horizontal = 16.dp),
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
//    Spacer(modifier = Modifier.height(8.dp))
    LazyVerticalGrid(
      cells = GridCells.Fixed(2),
      modifier = Modifier.padding(horizontal = 8.dp)
    ) {
      items(items = viewModel.state.value.plants) { item ->
        PlantCard(
          imageURL = item.imageURL,
          name = item.name,
          dateAdded = item.dateAdded,
          modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .width(200.dp)
//            .clickable {
//              Toast
//                .makeText(context, "card clicked", Toast.LENGTH_LONG)
//                .show()
//            }
        )
      }
      item() {
        Spacer(
          modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
        )
      }
      item() {
        Spacer(
          modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
        )
      }
    }
  }
}
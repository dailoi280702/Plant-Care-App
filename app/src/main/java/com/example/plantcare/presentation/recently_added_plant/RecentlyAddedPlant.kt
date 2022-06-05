package com.example.plantcare.presentation.recently_added_plant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.recently_added_plant.components.SmallPlantCard

@Composable
fun RecentlyAddedPlant(
  navController: NavController,
  viewModel: RecentlyAddedPlantViewModel = hiltViewModel()
) {
  val plants = viewModel.plants.value

  if (plants.isNotEmpty()) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Recently added plants",
          fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
        ClickableText(
          text = AnnotatedString("More"),
          onClick = {
            navController.navigate(Screens.MainScreens.Plants.route) {
              navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                  saveState = true
                }
              }
              launchSingleTop = true
              restoreState = true
            }
          },
          style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.tertiary),
        )
      }
      LazyRow(
        modifier = Modifier
          .fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 12.dp)
      ) {
        items(plants) { plant ->
          SmallPlantCard(plant = plant) {
            navController.navigate(Screens.AddPlantScreen.route + "?plantId=${plant.id}")
          }
        }
      }
    }
  }
}
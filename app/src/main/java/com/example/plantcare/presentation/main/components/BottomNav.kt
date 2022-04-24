package com.example.plantcare.presentation.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.plantcare.presentation.utils.Screens


@Composable
fun BottomNav(navController: NavController) {
  val items = listOf(
    Screens.MainScreens.Home,
    Screens.MainScreens.Plants,
    Screens.MainScreens.Tasks,
    Screens.MainScreens.Settings
  )

  BottomNavigation(
    backgroundColor = MaterialTheme.colors.surface
  ) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    items.forEach { item ->
      BottomNavigationItem(
        icon = {
          Icon(
            painter = painterResource(id = if (currentRoute == item.route) item.icon else item.outline_icon),
            contentDescription = item.title,
            modifier = Modifier.size(24.dp)
          )
        },
        selectedContentColor = MaterialTheme.colors.primary,
        unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.5f),
        label = {
          Text(text = item.title)
        },
        alwaysShowLabel = true,
        selected = currentRoute == item.route,
        onClick = {
          navController.navigate(item.route) {
            navController.graph.startDestinationRoute?.let { route ->
              popUpTo(route) {
                saveState = true
              }
            }
            launchSingleTop = true
            restoreState = true
          }
        }
      )
    }
  }
}

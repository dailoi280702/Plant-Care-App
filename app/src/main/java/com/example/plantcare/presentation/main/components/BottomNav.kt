package com.example.plantcare.presentation.main.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        icon = { Icon(imageVector = item.icon, contentDescription = item.title)},
//        label = { Text(text = item.title,
//          fontSize = 9.sp) },
        selectedContentColor = MaterialTheme.colors.primary,
        unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.5f),
//        alwaysShowLabel = true,
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

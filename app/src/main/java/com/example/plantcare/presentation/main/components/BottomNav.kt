package com.example.plantcare.presentation.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.plantcare.presentation.main.utils.Screens


@Composable
fun BottomNav(navController: NavController) {
  val items = listOf(
    Screens.MainScreens.Home,
    Screens.MainScreens.Plants,
    Screens.MainScreens.Tasks,
    Screens.MainScreens.Settings
  )
  
  NavigationBar(
  ) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    
    items.forEach { item ->
      val selected = currentRoute == item.route
      NavigationBarItem(
        icon = {
          Icon(
            painter = painterResource(id = if (selected) item.icon else item.outline_icon),
            contentDescription = item.title,
            modifier = Modifier.size(24.dp),
            tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
          )
        },
        label = {
          Text(
            text = item.title,
            color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = MaterialTheme.typography.labelMedium.fontSize,
            fontWeight = MaterialTheme.typography.labelMedium.fontWeight
          )
        },
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
        },
        colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.secondaryContainer)
      )
    }
  }
}

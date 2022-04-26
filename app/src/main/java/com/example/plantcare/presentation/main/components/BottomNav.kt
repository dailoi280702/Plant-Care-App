package com.example.plantcare.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.plantcare.presentation.utils.Screens
import com.example.plantcare.ui.theme.utils.customColors


@Composable
fun BottomNav(navController: NavController) {
  val items = listOf(
    Screens.MainScreens.Home,
    Screens.MainScreens.Plants,
    Screens.MainScreens.Tasks,
    Screens.MainScreens.Settings
  )

  BottomNavigation(
    backgroundColor = MaterialTheme.colors.surface,
    modifier = Modifier.height(72.dp),
  ) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    items.forEach { item ->
      BottomNavigationItem(
        icon = {
          Box(
            modifier = Modifier
              .padding(bottom = 4.dp)
              .height(32.dp),
            contentAlignment = Alignment.Center
          ) {
            if (currentRoute == item.route) {
              Surface(
                modifier = Modifier.size(width = 64.dp, height = 32.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.customColors.secondaryContainer
              ) {
              }
            }
            Icon(
              painter = painterResource(id = if (currentRoute == item.route) item.icon else item.outline_icon),
              contentDescription = item.title,
              modifier = Modifier.size(24.dp)
            )
          }
        },
        selectedContentColor = MaterialTheme.customColors.onSecondaryContainer,
//        unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.5f),
//        unselectedContentColor = MaterialTheme.colors.onSurface,
        unselectedContentColor = MaterialTheme.customColors.onSurfaceVariant,
        label = {
          Text(
            text = item.title,
            fontWeight = if (currentRoute == item.route) FontWeight.Bold else FontWeight.Normal
          )
        },
        alwaysShowLabel = false,
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

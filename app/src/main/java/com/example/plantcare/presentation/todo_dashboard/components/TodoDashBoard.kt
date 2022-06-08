package com.example.plantcare.presentation.todo_dashboard.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.todo_dashboard.TodoDashBoardViewModel
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold

@Composable
fun TodoDashBoard(
  navController: NavController,
  viewModel: TodoDashBoardViewModel = hiltViewModel()
) {
  
  val configuration = LocalConfiguration.current
  val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
  val state = viewModel.todoDashboardState.value
  
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .height(40.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = "Your todos",
        fontSize = MaterialTheme.typography.titleLarge.fontSize
      )
      ClickableText(
        text = AnnotatedString("More"),
        onClick = {
          navController.navigate(Screens.MainScreens.Tasks.route) {
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
    Row {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        ProgressCard(
          total = state.today,
          left = state.today - state.todayDone,
          isVerticalCard = isVertical
        ) {
          ProgressCardShortContent(
            text = "Today",
            total = state.today,
            left = state.today - state.todayDone
          )
        }
      }
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        ProgressCard(
          total = state.upComing,
          left = state.upComing - state.upComingDone,
          isVerticalCard = isVertical
        ) {
          ProgressCardShortContent(
            text = "UpComing",
            total = state.upComing,
            left = state.upComing - state.upComingDone
          )
        }
      }
    }
    Row() {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        ProgressCard(total = state.total, left = state.total - state.done, isVerticalCard = false) {
          ProgressCardLongContent(
            total = state.total,
            done = state.done,
            important = state.important,
            veryImportant = state.veryImportant,
            overdue = state.overdue
          )
        }
      }
      if (!isVertical) {
        Spacer(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        )
      }
    }
  }
}
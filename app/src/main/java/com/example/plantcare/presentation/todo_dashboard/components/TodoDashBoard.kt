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

}
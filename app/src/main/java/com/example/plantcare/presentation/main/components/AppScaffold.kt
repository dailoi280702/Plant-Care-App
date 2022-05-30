package com.example.plantcare.presentation

import android.annotation.SuppressLint
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.main.components.NavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
  authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
  val scaffoldState = rememberScaffoldState()
  val navController = rememberNavController()
  
  Scaffold {
    NavGraph(
      navController = navController,
      scaffoldState = scaffoldState,
      authenticationViewModel = authenticationViewModel
    )
  }
}
package com.example.plantcare.presentation.main.components

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.main.components.NavGraph
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppScaffold(
  authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
  val scaffoldState = rememberScaffoldState()
//  val navController = rememberAnimatedNavController()
  
  Scaffold {
    NavGraph(
//      navController = navController,
      scaffoldState = scaffoldState,
      authenticationViewModel = authenticationViewModel
    )
  }
}
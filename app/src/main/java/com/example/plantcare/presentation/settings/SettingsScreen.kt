package com.example.plantcare.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.login.LoginSignupEvent
import com.example.plantcare.presentation.login.LoginSignupUIEvent
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.utils.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
  navController: NavController,
  mainViewModel: MainViewModel,
  authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
  val context = LocalContext.current

  LaunchedEffect(key1 = true) {
    authenticationViewModel.eventFlow.collectLatest { event ->
      when (event) {
        is LoginSignupUIEvent.ShowText -> {
          Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
        }
        is LoginSignupUIEvent.Navigate -> {
          navController.navigate(event.screen.route) {
            popUpTo(Screens.MainScreens.Settings.route) {
              inclusive = true
            }
            mainViewModel.setCurrentScreen(event.screen)
          }
        }
      }
    }
  }

  Column(
    modifier = Modifier.fillMaxSize(), 
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Button(onClick = {authenticationViewModel.onEvent(LoginSignupEvent.SignOut)}) {
      Text(text = "log out")
    }
    Text(text = Firebase.auth.currentUser?.uid.toString())
  }
}

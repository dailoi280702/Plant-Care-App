package com.example.plantcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.presentation.main_screen.MainScreen
import com.example.plantcare.presentation.signIn_singUp.LoginSignupScreen
import com.example.plantcare.presentation.utils.Screen
import com.example.plantcare.ui.theme.PlantCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PlantCareTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          val navController = rememberNavController()
          NavHost(navController = navController, startDestination = Screen.LoginSignupScreen.route) {
            composable(route = Screen.LoginSignupScreen.route) {
              LoginSignupScreen(navController = navController)
            }
            composable(route = Screen.MainScreen.route) {
              MainScreen(navController = navController)
            }
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  PlantCareTheme {
    Greeting("Android")
  }
}
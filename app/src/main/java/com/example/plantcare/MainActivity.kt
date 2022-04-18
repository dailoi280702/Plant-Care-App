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
import com.example.plantcare.presentation.AppScaffold
import com.example.plantcare.ui.theme.PlantCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

//    val auth: FirebaseAuth = Firebase.auth
//    val startDest: String = if (auth.currentUser != null) Screen.MainScreen.route else Screen.LoginSignupScreen.route

    setContent {
      PlantCareTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//          val navController = rememberNavController()
//          NavHost(navController = navController, startDestination = startDest) {
//            composable(route = Screen.LoginSignupScreen.route) {
//              LoginSignupScreen(navController = navController)
//            }
//            composable(route = Screen.MainScreen.route) {
//              MainScreen()
//            }
//            composable(BottomNavItems.Home.route) {
//              HomeScreen(navController = navController)
//            }
//            composable(BottomNavItems.Plants.route) {
//              PlantsScreen(navController = navController)
//            }
//            composable(BottomNavItems.Tasks.route) {
//              TasksScreen(navController = navController)
//            }
//            composable(BottomNavItems.Settings.route) {
//              SettingsScreen(navController = navController)
//            }
//          }
          AppScaffold()
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
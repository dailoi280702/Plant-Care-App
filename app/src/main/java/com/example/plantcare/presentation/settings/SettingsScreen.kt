package com.example.plantcare.presentation.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.domain.utils.AppTheme
import com.example.plantcare.presentation.data_store.DataStoreViewModel
import com.example.plantcare.presentation.main.utils.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SettingsScreen(
  navController: NavController,
  dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
  viewModel: SettingsViewmodel = hiltViewModel(),
  bottomBar: @Composable () -> Unit
) {
  
  val context = LocalContext.current
  val theme = dataStoreViewModel.themeState.value
  val url: String = stringResource(id = R.string.github_page)
  val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }
  
  Scaffold(bottomBar = bottomBar) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 8.dp)
      ) {
        Text(
          text = "Theme",
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleMedium
        )
        AnimatedContent(targetState = dataStoreViewModel.themeState) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = theme.text, color = MaterialTheme.colorScheme.onSurface)
            IconButton(onClick = { dataStoreViewModel.switchMode() }) {
              Icon(
                painter = painterResource(
                  id = when (theme) {
                    is AppTheme.Auto -> R.drawable.ic_night_sight_auto_fill0_wght400_grad0_opsz48
                    is AppTheme.Light -> R.drawable.ic_light_mode_fill0_wght400_grad0_opsz48
                    is AppTheme.Dark -> R.drawable.ic_dark_mode_fill0_wght400_grad0_opsz48
                  }
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }
      }
      Divider()
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 8.dp)
      ) {
        Text(
          text = "Language",
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleMedium
        )
        AnimatedContent(targetState = dataStoreViewModel.themeState) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "English", color = MaterialTheme.colorScheme.onSurface)
            IconButton(onClick = {
              Toast.makeText(
                context,
                "Other languages not supported yet!",
                Toast.LENGTH_SHORT
              ).show()
            }) {
              Icon(
                painter = painterResource(id = R.drawable.language_fill0_wght400_grad0_opsz48),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }
      }
      Divider()
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 8.dp)
      ) {
        Text(
          text = "User",
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleMedium
        )
        AnimatedContent(targetState = dataStoreViewModel.themeState) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Logout", color = MaterialTheme.colorScheme.onSurface)
            IconButton(onClick = {
              viewModel.signOut()
              navController.navigate(Screens.LoginSignupScreen.route) {
                popUpTo(0)
              }
            }) {
              Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }
      }
      Divider()
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 8.dp)
      ) {
        Text(
          text = "About",
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleMedium
        )
        AnimatedContent(targetState = dataStoreViewModel.themeState) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Check out our website", color = MaterialTheme.colorScheme.onSurface)
            IconButton(onClick = { context.startActivity(intent) }) {
              Icon(
                painter = painterResource(id = R.drawable.ic_public_fill0_wght400_grad0_opsz48),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }
      }
      Divider()
    }
  }
}

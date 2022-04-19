package com.example.plantcare.presentation.login_signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.presentation.login_signup.components.LoginTab
import com.example.plantcare.presentation.login_signup.components.SignupTab
import com.example.plantcare.presentation.login_signup.utils.LoginIconButton
import com.example.plantcare.presentation.login_signup.utils.LoginSignupTabItem
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.utils.Screens
import com.example.plantcare.ui.theme.Facebook_color
import com.example.plantcare.ui.theme.Google_color
import com.example.plantcare.ui.theme.Twitter_color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginSignupScreen(
  navController: NavController,
  mainViewModel: MainViewModel,
  viewModel: AuthenticationViewModel = hiltViewModel()
) {

  val loginTextState = viewModel.loginEmailPassword.value
  val signupTextState = viewModel.signupEmailPassword.value
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current

  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest { event ->
      when (event) {
        is LoginSignupUIEvent.ShowText -> {
          Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
        }
        is LoginSignupUIEvent.Navigate -> {
          navController.navigate(event.screen.route) {
            popUpTo(Screens.LoginSignupScreen.route) {
              inclusive = true
            }
            mainViewModel.setCurrentScreen(event.screen)
          }
        }
      }
    }
  }

  val tabList = listOf(
    LoginSignupTabItem.Login {
      LoginTab(
        title = stringResource(id = R.string.login_title),
        description = stringResource(id = R.string.login_description),
        buttonText = "log in",
        email = loginTextState.email,
        password = loginTextState.password,
        focusManager = focusManager,
        onEmailChange = { viewModel.onEvent(LoginSignupEvent.EnterLoginEmail(it)) },
        onPasswordChange = { viewModel.onEvent(LoginSignupEvent.EnterLoginPassword(it)) }
      ) {
        viewModel.onEvent(LoginSignupEvent.LoginWithEmailAndPassword)
      }
    },
    LoginSignupTabItem.Signup {
      SignupTab(
        title = stringResource(id = R.string.signup_title),
        description = stringResource(id = R.string.signup_description),
        buttonText = "sign up",
        email = signupTextState.email,
        password = signupTextState.password,
        confirmPassword = signupTextState.confirmPassword,
        focusManager = focusManager,
        onEmailChange = { viewModel.onEvent(LoginSignupEvent.EnterSignupEmail(it)) },
        onPasswordChange = { viewModel.onEvent(LoginSignupEvent.EnterSignupPassword(it)) },
        onConfirmPasswordChange = { viewModel.onEvent(LoginSignupEvent.EnterSignupConfirmPassword(it)) }
      ) {
        viewModel.onEvent(LoginSignupEvent.SignupWithEmailAndPassword)
      }
    }
  )
  val pagerState = rememberPagerState()
  val scope = rememberCoroutineScope()

  Box(
    modifier = Modifier.fillMaxSize(),
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      Image(
        painter = painterResource(id = R.drawable.pexels_irina_iriser_943905),
        contentDescription = "Login Signup background",
        modifier = Modifier.weight(1f),
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth
      )
      Spacer(modifier = Modifier.weight(3f))
    }
  }
  Box(
    modifier = Modifier.fillMaxSize(),
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .background(
            brush = Brush.horizontalGradient(
              colors = listOf(
                Color.Transparent,
                Color.Black.copy(alpha = 0.5f)
              )
            )
          )
      )
      Spacer(modifier = Modifier.weight(3f))
    }
  }
  Box(
    modifier = Modifier.fillMaxWidth(),
    contentAlignment = Alignment.TopCenter,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 32.dp, vertical = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "PlantCare",
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.h3.fontSize,
        color = Color.White,
        modifier = Modifier.padding(bottom = 24.dp),
        fontFamily = FontFamily.Cursive
      )
      Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp), elevation = 12.dp
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          TabRow(selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
              .padding(vertical = 12.dp, horizontal = 8.dp)
              .clip(
                RoundedCornerShape(
                  topEnd = 20.dp,
                  topStart = 20.dp,
                  bottomStart = 0.dp,
                  bottomEnd = 0.dp
                )
              ),
            indicator = {
              Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = it)
            }
          ) {
            tabList.forEachIndexed { index, tabItem ->
              val selected = pagerState.currentPage == index
              Tab(
                selected = selected,
                onClick = {
                  scope.launch {
                    pagerState.animateScrollToPage(index)
                    focusManager.clearFocus()
                  }
                },
                text = {
                  Text(
                    text = tabItem.tabTitle,
                    color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground.copy(
                      alpha = 0.5f
                    ),
                    fontSize = MaterialTheme.typography.h6.fontSize
                  )
                }
              )
            }
          }
          HorizontalPager(count = tabList.count(), state = pagerState) {
            tabList[it].screen()
          }
          Column(
            modifier = Modifier
              .padding(top = 12.dp)
              .fillMaxWidth()
              .background(Color(0xFF4d8076).copy(alpha = 0.05f))
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Text(
              text = stringResource(id = R.string.login_another),
              fontSize = MaterialTheme.typography.body2.fontSize,
              textAlign = TextAlign.Center,
              color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
              modifier = Modifier.padding(horizontal = 12.dp)
            )
            Row(
              modifier = Modifier
                .fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              LoginIconButton(
                icon = painterResource(id = R.drawable.ic_logo_google),
                contentDescription = "Google Icon",
                backgroundColor = Google_color
              ) {
                viewModel.onEvent(LoginSignupEvent.LoginWithGoogle)
              }
              LoginIconButton(
                icon = painterResource(id = R.drawable.ic_logo_facebook),
                contentDescription = "Facebook Icon",
                backgroundColor = Facebook_color
              ) {
                viewModel.onEvent(LoginSignupEvent.LoginWithFaceBook)
              }
              LoginIconButton(
                icon = painterResource(id = R.drawable.ic_logo_twitter),
                contentDescription = "Twitter Icon",
                backgroundColor = Twitter_color
              ) {
                viewModel.onEvent(LoginSignupEvent.LoginWithTwitter)
              }
            }
          }
        }
      }
    }
  }
}
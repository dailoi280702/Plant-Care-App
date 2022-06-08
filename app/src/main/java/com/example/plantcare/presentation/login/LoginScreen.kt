package com.example.plantcare.presentation.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.presentation.login.components.LoginTab
import com.example.plantcare.presentation.login.components.SignupTab
import com.example.plantcare.presentation.login.utils.LoginIconButton
import com.example.plantcare.presentation.login.utils.LoginSignupTabItem
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.ui.theme.Facebook_color
import com.example.plantcare.ui.theme.Google_color
import com.example.plantcare.ui.theme.Twitter_color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun LoginSignupScreen(
  navController: NavController,
  viewModel: AuthenticationViewModel = hiltViewModel()
) {
  
  val loginTextState = viewModel.loginEmailPassword.value
  val signupTextState = viewModel.signupEmailPassword.value
  val recoveryEmail = viewModel.recoveryEmail.value
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current
  val pagerState = rememberPagerState()
  val scope = rememberCoroutineScope()
  val token = stringResource(id = R.string.client_id)
  val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
  val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
  val dimBackGround by animateColorAsState(
    targetValue = if (sheetState.isExpanded) Color.Black.copy(
      alpha = 0.7f
    ) else Color.Transparent
  )
  val onSendRecoveryEmailClick: () -> Unit = {
    focusManager.clearFocus()
    scope.launch {
      if (sheetState.isExpanded) {
        sheetState.collapse()
      }
    }
    viewModel.onEvent(LoginSignupEvent.SendRecoveryEmail)
    viewModel.onEvent(LoginSignupEvent.EnterRecoveryEmail(""))
  }
  
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
          }
        }
      }
    }
  }
  
  val launcher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
      val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
      try {
        
        val account = task.getResult(ApiException::class.java)
        account?.let { gsa ->
          gsa.idToken?.let { idToken ->
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            viewModel.onEvent(LoginSignupEvent.LoginWithGoogle(credential))
          }
        }
      } catch (e: ApiException) {
        Toast.makeText(context, "Google sign in failed!", Toast.LENGTH_SHORT).show()
      }
    }
  
  val tabList = listOf(
    LoginSignupTabItem.Login {
      LoginTab(
        title = stringResource(id = R.string.login_title),
        description = stringResource(id = R.string.login_description),
        buttonText = "Log In",
        email = loginTextState.email,
        password = loginTextState.password,
        focusManager = focusManager,
        onEmailChange = { viewModel.onEvent(LoginSignupEvent.EnterLoginEmail(it)) },
        onPasswordChange = { viewModel.onEvent(LoginSignupEvent.EnterLoginPassword(it)) },
        onForgotPasswordClick = {
          focusManager.clearFocus()
          scope.launch {
            if (sheetState.isCollapsed) {
              sheetState.expand()
            }
          }
        }
      ) {
        viewModel.onEvent(LoginSignupEvent.LoginWithEmailAndPassword)
      }
    },
    LoginSignupTabItem.Signup {
      SignupTab(
        title = stringResource(id = R.string.signup_title),
        description = stringResource(id = R.string.signup_description),
        buttonText = "Sign Up",
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
  
  val content: @Composable () -> Unit = {
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
          fontSize = MaterialTheme.typography.headlineMedium.fontSize,
          color = Color.White,
          modifier = Modifier.padding(bottom = 24.dp),
          fontFamily = FontFamily.Cursive
        )
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(28.dp)
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
              selectedTabIndex = pagerState.currentPage,
              modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp)),
              indicator = {
//              Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = it)
              },
              containerColor = Color.Transparent
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
                      color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.5f
                      ),
                      fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                  }
                )
              }
            }
            Divider(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
            HorizontalPager(count = tabList.count(), state = pagerState) {
              tabList[it].screen()
            }
            Column(
              modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 24.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
              Divider()
              Text(
                text = stringResource(id = R.string.login_another),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
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
                  val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
                  
                  val googleSignInClient = GoogleSignIn.getClient(context, gso)
                  googleSignInClient.revokeAccess()
                  launcher.launch(googleSignInClient.signInIntent)
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
              Spacer(modifier = Modifier.height(24.dp))
            }
          }
        }
      }
    }
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(dimBackGround)
    )
  }
  
  BottomSheetScaffold(
    scaffoldState = scaffoldState,
    sheetPeekHeight = 0.dp,
    sheetBackgroundColor = Color.Transparent,
    sheetContentColor = MaterialTheme.colorScheme.onBackground,
    sheetContent = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp)
          .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
          .background(MaterialTheme.colorScheme.background)
          .padding(24.dp)
      ) {
        Text(
          text = "Enter your recovery email",
          style = MaterialTheme.typography.titleLarge,
        )
        OutlinedTextField(
          value = recoveryEmail,
          onValueChange = { viewModel.onEvent(LoginSignupEvent.EnterRecoveryEmail(it)) },
          singleLine = true,
          colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
          ),
          shape = RoundedCornerShape(8.dp),
          leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
          },
          trailingIcon = {
            if (recoveryEmail.isNotBlank() && recoveryEmail.isNotEmpty()) {
              IconButton(onClick = { viewModel.onEvent(LoginSignupEvent.EnterRecoveryEmail("")) }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
              }
            }
          },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Send
          ),
          keyboardActions = KeyboardActions(
            onSend = { onSendRecoveryEmailClick() }
          )
        )
        Button(onClick = { onSendRecoveryEmailClick() }) {
          Text(text = "Send me recovery email")
        }
      }
    }
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
      content()
    }
  }
  
  if (viewModel.dataState.value is DataState.Loading) {
    Dialog(onDismissRequest = {}) {
      CircularProgressIndicator()
    }
  }
}
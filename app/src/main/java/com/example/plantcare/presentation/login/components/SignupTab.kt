package com.example.plantcare.presentation.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantcare.R

@Composable
fun SignupTab(
  title: String,
  description: String,
  buttonText: String,
  email: String,
  password: String,
  confirmPassword: String,
  focusManager: FocusManager,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onConfirmPasswordChange: (String) -> Unit,
  onButtonClick: () -> Unit,
) {
  val showPassword = remember {
    mutableStateOf(false)
  }

  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = title,
      fontSize = MaterialTheme.typography.titleLarge.fontSize,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(8.dp),
      color = MaterialTheme.colorScheme.onSurface
    )
    Text(
      text = description,
      fontSize = MaterialTheme.typography.titleSmall.fontSize,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(horizontal = 8.dp),
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      OutlinedTextField(
        value = email, onValueChange = {
          onEmailChange(it)
        },
        modifier = Modifier
          .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Enter email") },
        singleLine = true,
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = "Mail Icon",
            tint = MaterialTheme.colorScheme.primary
          )
        },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
          onNext = {
            focusManager.moveFocus(FocusDirection.Down)
          }
        )
      )
      OutlinedTextField(
        value = password,
        onValueChange = {
          onPasswordChange(it)
        },
        modifier = Modifier
          .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Enter password") },
        singleLine = true,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Password Icon",
            tint = MaterialTheme.colorScheme.primary
          )
        },
        trailingIcon = {
          IconButton(onClick = { showPassword.value = !showPassword.value }) {
            Icon(
              painter = painterResource(id = if (showPassword.value) R.drawable.ic_eye_outline else R.drawable.ic_eye_off_outline),
              contentDescription = "Password Icon",
            )
          }
        },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Password,
          imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
          onNext = {
            focusManager.moveFocus(FocusDirection.Down)
          }
        )
      )
      OutlinedTextField(
        value = confirmPassword,
        onValueChange = {
          onConfirmPasswordChange(it)
        },
        modifier = Modifier
          .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = "Confirm Password") },
        placeholder = { Text(text = "Confirm Password") },
        singleLine = true,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Password Icon",
            tint = if (password != confirmPassword && confirmPassword.isNotBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
          )
        },
        trailingIcon = {
          if (confirmPassword.isNotBlank()) {
            Icon(
              imageVector = if (confirmPassword == password) Icons.Default.Done else Icons.Default.Close,
              contentDescription = "Confirm Password Icon",
              tint = if (confirmPassword != password && confirmPassword.isNotBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
          }
        },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Password,
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = {
            focusManager.clearFocus()
          }
        ),
        isError = confirmPassword != password && confirmPassword.isNotBlank()
      )
      Button(
        onClick = {
          onButtonClick()
        },
        Modifier
          .padding(top = 8.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = buttonText,
          fontSize = MaterialTheme.typography.labelLarge.fontSize,
          modifier = Modifier.padding(8.dp),
          color = MaterialTheme.colorScheme.onPrimary
        )
      }
    }
  }
}

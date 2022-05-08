package com.example.plantcare.presentation.add_edit_task;

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plantcare.R
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditTaskDialog(
  openDiaLog: Boolean,
  plantTask: PlantTask?,
  viewModel: AddEditTaskDialogViewModel = hiltViewModel(),
  onDismiss: () -> Unit
) {

  LaunchedEffect(Unit) {
    plantTask?.let {
      viewModel.initTask(it)
    }
  }

  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest {
      if (it) {
        onDismiss()
      }
    }
  }

  val taskValue = viewModel.plantTask.value
  val importantText = when (taskValue.important) {
    1 -> "not important"
    2 -> "important"
    else -> "very important"
  }
  val iconId = when (taskValue.important) {
    1 -> R.drawable.ic_star_half
    2 -> R.drawable.ic_star
    else -> R.drawable.ic_star_outline
  }
  val textColor = when (taskValue.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.onSurface
  }
  val iconColor = when (taskValue.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.surfaceTint
  }
  val showError = viewModel.errorMessage.value != null

  if (openDiaLog) {
    AlertDialog(
      onDismissRequest = onDismiss,
      title = {
        Text(text = if (taskValue.taskId == "") "New todo" else "Update todo")
      },
      text = {
        Column {
          OutlinedTextField(
            value = taskValue.title!!,
            onValueChange = {
              viewModel.onEvent(AddEditTaskDialogEvent.UpdateTitle(it))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
              unfocusedBorderColor = Color.Transparent,
              focusedBorderColor = Color.Transparent
            ),
            placeholder = {
              Text(text = "Enter title")
            }
          )
          OutlinedTextField(
            value = taskValue.description!!,
            onValueChange = {
              viewModel.onEvent(AddEditTaskDialogEvent.UpdateDescription(it))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
              unfocusedBorderColor = Color.Transparent,
              focusedBorderColor = Color.Transparent
            ),
            placeholder = {
              Text(text = "Enter description")
            }
          )
          Spacer(modifier = Modifier.height(16.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
              onClick = {
                Log.d("asd123xxx", "ahdsflk")
                viewModel.onEvent(AddEditTaskDialogEvent.UpdateTaskImportant)
              },
              modifier = Modifier.padding(horizontal = 8.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  painter = painterResource(id = iconId),
                  contentDescription = null,
                  tint = iconColor,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = importantText,
                  color = textColor,
                )
              }
            }
          }
          Row(
            modifier = Modifier
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            Text(
              text = viewModel.errorMessage.value ?: "",
              color = MaterialTheme.colorScheme.error
            )
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.onEvent(AddEditTaskDialogEvent.AddTask)
          }
        ) {
          Text("Add")
        }
      },
      dismissButton = {
        TextButton(
          onClick = onDismiss
        ) {
          Text("Dismiss")
        }
      },
    )
  }
}

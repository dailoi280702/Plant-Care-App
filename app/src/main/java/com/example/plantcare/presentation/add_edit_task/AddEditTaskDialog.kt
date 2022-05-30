package com.example.plantcare.presentation.add_edit_task

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.plantcare.R
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskDialog(
  viewModel: AddEditTaskDialogViewModel,
) {
  
  val onDismiss: () -> Unit =
    { viewModel.onEvent(AddEditTaskDialogEvent.UpdateDialogVisibility(false)) }
  
  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest {
      if (it) {
        onDismiss()
      }
    }
  }
  
  val state = viewModel.addEditTaskDialogState.value
  val isNewTodo = state.todo.todoId == "" || state.todo.todoId == null
  var showNumberPicker by remember { mutableStateOf(false) }
  val durations = listOf(Duration.DAY, Duration.WEEK, Duration.MONTH)
  var durationState by remember { mutableStateOf(durations[0]) }
  val datePickerDialog = DatePickerDialog(
    LocalContext.current,
    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
      viewModel.onEvent(
        AddEditTaskDialogEvent.UpdateDueDate(
          day = dayOfMonth,
          month = month,
          year = year
        )
      )
    },
    state.year,
    state.month,
    state.day
  )
  
  val importantText = when (state.todo.important) {
    1 -> "Important"
    2 -> "Very important"
    else -> "Not important"
  }
  val iconId = when (state.todo.important) {
    1 -> R.drawable.ic_star_half
    2 -> R.drawable.ic_star
    else -> R.drawable.ic_star_outline
  }
  val textColor = when (state.todo.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.onSurface
  }
  val iconColor = when (state.todo.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.onSurface
  }
  
  val numberPickerDialog: @Composable () -> Unit = {
    DropdownMenu(
      expanded = showNumberPicker && state.todo.repeatable,
      onDismissRequest = { showNumberPicker = !showNumberPicker },
      modifier = Modifier.padding(horizontal = 24.dp)
    ) {
      Row {
        NumberPicker(
          value = state.duration.time,
          range = 1..100,
          onValueChange = {
            viewModel.onEvent(
              AddEditTaskDialogEvent.UpdateDuration(
                state.duration.copy(
                  it
                )
              )
            )
          },
          dividersColor = Color.Transparent,
          textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        )
        ListItemPicker(
          label = { it },
          value = durationState,
          onValueChange = {
            durationState = it
            viewModel.onEvent(
              AddEditTaskDialogEvent.UpdateDuration(
                state.duration.copy(
                  it
                )
              )
            )
          },
          list = durations,
          dividersColor = Color.Transparent,
          textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        )
      }
    }
  }
  
  if (state.visible) {
    AlertDialog(
      onDismissRequest = onDismiss,
      title = {
        Text(text = if (isNewTodo) "New Todo" else "Todo")
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = state.todo.title ?: "",
            onValueChange = {
              viewModel.onEvent(AddEditTaskDialogEvent.UpdateTitle(it))
            },
            placeholder = {
              Text(text = "Enter title *")
            },
            textStyle = MaterialTheme.typography.titleMedium,
            singleLine = true
          )
          OutlinedTextField(
            value = importantText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
              IconButton(onClick = {
                viewModel.onEvent(AddEditTaskDialogEvent.UpdateTaskImportant)
              }) {
                Icon(
                  painter = painterResource(id = iconId),
                  contentDescription = null,
                  tint = iconColor,
                  modifier = Modifier.size(24.dp)
                )
              }
            }
          )
          
          OutlinedTextField(
            value = "Due at: ${state.day}/${state.month + 1}/${state.year}",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
              IconButton(onClick = {
                datePickerDialog.show()
              }) {
                Icon(
                  painter = painterResource(id = R.drawable.ic_event_today),
                  contentDescription = null,
                  modifier = Modifier.size(24.dp)
                )
              }
            }
          )
          
          OutlinedTextField(
            value = "Repeat after: ${state.duration.time} ${state.duration.type}${if (state.duration.time == 1) "s" else ""}",
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
              Checkbox(
                checked = state.todo.repeatable,
                onCheckedChange = { viewModel.onEvent(AddEditTaskDialogEvent.UpdateRepeatable) }
              )
            },
            trailingIcon = {
              Box {
                IconButton(onClick = {
                  showNumberPicker = !showNumberPicker
                }) {
                  Icon(
                    painter = painterResource(id = R.drawable.ic_repeat),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                  )
                }
                numberPickerDialog()
              }
            },
            enabled = state.todo.repeatable
          )
          
          Row(
            modifier = Modifier
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            Text(
              text = viewModel.errorMessage.value ?: "",
              color = MaterialTheme.colorScheme.error,
              maxLines = 1
            )
          }
        }
      },
      confirmButton = {
        TextButton(
          onClick = {
            if (isNewTodo) {
              viewModel.onEvent(AddEditTaskDialogEvent.AddTask)
            } else {
              viewModel.onEvent(AddEditTaskDialogEvent.UpdateTask)
            }
          }
        ) {
          Text(
            if (isNewTodo) "Add" else "Update"
          )
        }
      },
      dismissButton = {
        TextButton(
          onClick = onDismiss
        ) {
          Text("Cancel")
        }
      }
    )
  }
}
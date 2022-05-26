package com.example.plantcare.presentation.add_edit_task

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.plantcare.R
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold
import kotlinx.coroutines.flow.collectLatest

@OptIn(
  ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
  ExperimentalAnimationApi::class
)
@Composable
fun AddEditTaskDialog(
  openDiaLog: Boolean,
  plantTask: PlantTask? = null,
  plantId: String?,
  viewModel: AddEditTaskDialogViewModel = hiltViewModel(),
  onDismiss: () -> Unit
) {
  
  LaunchedEffect(Unit) {
    plantId?.let {
      viewModel.init(plantId, plantTask)
    }
  }
  
  LaunchedEffect(plantTask) {
    viewModel.init(plantId, plantTask)
  }
  
  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest {
      if (it) {
        onDismiss()
        plantId?.let {
          viewModel.init(plantId, null)
        }
      }
    }
  }
  
  val stateValue = viewModel.addEditTaskDialogState.value
  
  var showNumberPicker by remember { mutableStateOf(false) }
  val possibleValues = listOf(Duration.DAY, Duration.WEEK, Duration.MONTH)
  var state by remember { mutableStateOf(possibleValues[0]) }
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
    stateValue.year,
    stateValue.month,
    stateValue.day
  )
  
  
  val importantText = when (stateValue.important) {
    1 -> "important"
    2 -> "very important"
    else -> "not important"
  }
  val iconId = when (stateValue.important) {
    1 -> R.drawable.ic_star_half
    2 -> R.drawable.ic_star
    else -> R.drawable.ic_star_outline
  }
  val textColor = when (stateValue.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.onSurface
  }
  val iconColor = when (stateValue.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.onSurface
  }
  
  val numberPickerDialog: @Composable () -> Unit = {
    DropdownMenu(
      expanded = showNumberPicker && stateValue.repeatable,
      onDismissRequest = { showNumberPicker = !showNumberPicker },
      modifier = Modifier.padding(horizontal = 24.dp)
    ) {
      Row {
        NumberPicker(
          value = stateValue.duration.time,
          range = 1..100,
          onValueChange = {
            viewModel.onEvent(
              AddEditTaskDialogEvent.UpdateDuration(
                stateValue.duration.copy(
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
          value = state,
          onValueChange = {
            state = it
            viewModel.onEvent(
              AddEditTaskDialogEvent.UpdateDuration(
                stateValue.duration.copy(
                  it
                )
              )
            )
          },
          list = possibleValues,
          dividersColor = Color.Transparent,
          textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        )
      }
    }
  }
  
  AnimatedVisibility(visible = openDiaLog) {
    AlertDialog(
      onDismissRequest = onDismiss,
      title = {
              Text(text = if (viewModel.currentPlantTask.value != null) "Todo" else "New Todo")
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          AnimatedContent(targetState = plantTask) {
            OutlinedTextField(
              value = stateValue.title,
              onValueChange = {
                viewModel.onEvent(AddEditTaskDialogEvent.UpdateTitle(it))
              },
              placeholder = {
                Text(text = "Enter title *")
              },
              textStyle = MaterialTheme.typography.titleMedium
            )
          }
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
            value = "Due at: ${stateValue.day}/${stateValue.month + 1}/${stateValue.year}",
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
            value = "repeat after: ${stateValue.duration.time} ${stateValue.duration.type}",
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
              Checkbox(
                checked = stateValue.repeatable,
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
            enabled = stateValue.repeatable
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
            if (viewModel.currentPlantTask.value == null) {
              viewModel.onEvent(AddEditTaskDialogEvent.AddTask)
            } else {
              viewModel.onEvent(AddEditTaskDialogEvent.UpdateTask)
            }
          }
        ) {
          Text(
            if (viewModel.currentPlantTask.value == null) "Add" else "Update"
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
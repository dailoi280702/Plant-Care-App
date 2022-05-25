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
import androidx.compose.ui.unit.dp
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

  AnimatedVisibility(visible = openDiaLog) {
//  }
//  if (openDiaLog) {
    AlertDialog(
      onDismissRequest = onDismiss,
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          AnimatedContent(targetState = plantTask) {
            OutlinedTextField(
              value = stateValue.title,
              onValueChange = {
                viewModel.onEvent(AddEditTaskDialogEvent.UpdateTitle(it))
              },
//              colors = TextFieldDefaults.outlinedTextFieldColors(
//                unfocusedBorderColor = Color.Transparent,
//                focusedBorderColor = Color.Transparent
//              ),
              placeholder = {
                Text(text = "Enter title")
              },
              textStyle = MaterialTheme.typography.titleMedium
            )
          }

//          Divider()
//
//          ContentContainer(icon = {
//            Icon(
//              painter = painterResource(id = iconId),
//              contentDescription = null,
//              tint = iconColor,
//              modifier = Modifier.size(24.dp)
//            )
//          }) {
//            TextButton(onClick = {
//              viewModel.onEvent(AddEditTaskDialogEvent.UpdateTaskImportant)
//            }) {
//              Text(
//                text = importantText,
//                color = textColor,
//              )
//            }
//          }
          OutlinedTextField(
            value = importantText,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
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
            },
            trailingIcon = {
              IconButton(onClick = {
                viewModel.onEvent(AddEditTaskDialogEvent.UpdateTaskImportant)
              }) {
                Box() {
                  Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                  )
                }
              }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
          )

//          ContentContainer(icon = {
//            Icon(
//              painter = painterResource(id = R.drawable.ic_event_today),
//              contentDescription = null,
//              modifier = Modifier.size(24.dp)
//            )
//          }) {
//            TextButton(onClick = {
//              datePickerDialog.show()
//            }) {
//              Text(text = "Due at: ${stateValue.day}/${stateValue.month + 1}/${stateValue.year}")
//            }
//          }
          OutlinedTextField(
            value = "Due at: ${stateValue.day}/${stateValue.month + 1}/${stateValue.year}",
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
              IconButton(onClick = {
                datePickerDialog.show()
              }) {
                Icon(
                  painter = painterResource(id = R.drawable.ic_event_today),
                  contentDescription = null,
                  modifier = Modifier.size(24.dp)
                )
              }
            },
            trailingIcon = {
              IconButton(onClick = {
                datePickerDialog.show()
              }) {
                Box() {
                  Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                  )
                }
              }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
          )

//          ContentContainer(
//            icon = {
//              Icon(
//                painter = painterResource(id = R.drawable.ic_repeat),
//                contentDescription = null,
//                modifier = Modifier.size(24.dp)
//              )
//            }, showBottomLine = false
//          ) {
//            Box {
//              Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//              ) {
//                TextButton(
//                  onClick = {
//                    showNumberPicker = !showNumberPicker
//                  },
//                  enabled = stateValue.repeatable,
//                  colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    contentColor = MaterialTheme.colorScheme.primary
//                  )
//                ) {
//                  Text(text = "repeat after: ${stateValue.duration.time} ${stateValue.duration.type}")
//                }
//                Checkbox(
//                  checked = stateValue.repeatable,
//                  onCheckedChange = { viewModel.onEvent(AddEditTaskDialogEvent.UpdateRepeatable) }
//                )
//              }
//              DropdownMenu(
//                expanded = showNumberPicker && stateValue.repeatable,
//                onDismissRequest = { showNumberPicker = !showNumberPicker }) {
//                Row {
//                  NumberPicker(
//                    value = stateValue.duration.time,
//                    range = 1..100,
//                    onValueChange = {
//                      viewModel.onEvent(
//                        AddEditTaskDialogEvent.UpdateDuration(
//                          stateValue.duration.copy(
//                            it
//                          )
//                        )
//                      )
//                    },
//                    dividersColor = Color.Transparent,
//                    textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface),
//                  )
//                  ListItemPicker(
//                    label = { it },
//                    value = state,
//                    onValueChange = {
//                      state = it
//                      viewModel.onEvent(
//                        AddEditTaskDialogEvent.UpdateDuration(
//                          stateValue.duration.copy(
//                            it
//                          )
//                        )
//                      )
//                    },
//                    list = possibleValues,
//                    dividersColor = Color.Transparent,
//                    textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface),
//                  )
//                }
//              }
//            }
//          }
          OutlinedTextField(
            value = "repeat after: ${stateValue.duration.time} ${stateValue.duration.type}",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
              Checkbox(
                checked = stateValue.repeatable,
                onCheckedChange = { viewModel.onEvent(AddEditTaskDialogEvent.UpdateRepeatable) }
              )
            },
            leadingIcon = {
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
                DropdownMenu(
                  expanded = showNumberPicker && stateValue.repeatable,
                  onDismissRequest = { showNumberPicker = !showNumberPicker }) {
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
              color = MaterialTheme.colorScheme.error
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

@Composable
fun ContentContainer(
  showBottomLine: Boolean = true,
  icon: @Composable () -> Unit,
  content: @Composable () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
      icon()
    }
    content()
  }
  if (showBottomLine) {
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      Spacer(modifier = Modifier.width(40.dp))
      Divider()
    }
  }
}

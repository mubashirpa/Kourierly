package com.evaluation.kourierly.presentation.customerUpdate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.evaluation.kourierly.R
import com.evaluation.kourierly.presentation.components.ProgressDialog
import com.evaluation.kourierly.presentation.theme.KourierlyTheme
import kotlinx.coroutines.flow.filter
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomerUpdateScreen(
    phoneNumber: String,
    onUpdateSuccess: (customerName: String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CustomerUpdateViewModel = koinViewModel(),
) {
    val currentOnUpdateSuccess by rememberUpdatedState(onUpdateSuccess)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(viewModel, lifecycle) {
        snapshotFlow { viewModel.uiState }
            .filter { it.customerUpdateSuccess }
            .flowWithLifecycle(lifecycle)
            .collect {
                currentOnUpdateSuccess(
                    viewModel.uiState.nameState.text
                        .toString(),
                )
            }
    }

    CustomerUpdateScreenContent(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        phoneNumber = phoneNumber,
        onNavigateUp = onNavigateUp,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomerUpdateScreenContent(
    uiState: CustomerUpdateUiState,
    onEvent: (CustomerUpdateUiEvent) -> Unit,
    phoneNumber: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackbarHostState.showSnackbar(userMessage)
            // Once the message is displayed and dismissed, notify the ViewModel.
            onEvent(CustomerUpdateUiEvent.UserMessageShown)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.basic_information),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = uiState.success,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            ) {
                Column {
                    HorizontalDivider()
                    Row(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp, vertical = 24.dp)
                                .navigationBarsPadding(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        OutlinedButton(
                            onClick = onNavigateUp,
                            modifier =
                                Modifier
                                    .heightIn(min = 56.dp)
                                    .weight(1f),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(
                                text = stringResource(R.string.previous),
                                style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                            )
                        }
                        Button(
                            onClick = {
                                onEvent(
                                    CustomerUpdateUiEvent.UpdateCustomer(
                                        name = uiState.nameState.text.toString(),
                                        gender = uiState.genderState.text.toString(),
                                    ),
                                )
                            },
                            modifier =
                                Modifier
                                    .heightIn(min = 56.dp)
                                    .weight(1f),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(
                                text = stringResource(R.string.done),
                                style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                            )
                        }
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                when {
                    uiState.loading -> {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center),
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    uiState.success -> {
                        ExposedDropdownMenuBox(
                            expanded = uiState.expandedRolesDropdown,
                            onExpandedChange = {
                                onEvent(CustomerUpdateUiEvent.OnRolesDropdownExpandedChange(it))
                            },
                        ) {
                            OutlinedTextField(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                                state = uiState.roleState,
                                readOnly = true,
                                lineLimits = TextFieldLineLimits.SingleLine,
                                labelPosition = TextFieldLabelPosition.Attached(true),
                                label = {
                                    Text(text = stringResource(R.string.select_your_role))
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.expandedRolesDropdown)
                                },
                                keyboardOptions =
                                    KeyboardOptions(
                                        imeAction = ImeAction.Next,
                                    ),
                                onKeyboardAction = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                },
                                shape = MaterialTheme.shapes.medium,
                            )
                            ExposedDropdownMenu(
                                expanded = uiState.expandedRolesDropdown,
                                onDismissRequest = {
                                    onEvent(
                                        CustomerUpdateUiEvent.OnRolesDropdownExpandedChange(
                                            false,
                                        ),
                                    )
                                },
                            ) {
                                uiState.roles.forEach { role ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(
                                                    text = role.customerRole.toString(),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                )
                                                Text(
                                                    text = role.description.toString(),
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 1,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        },
                                        onClick = {
                                            uiState.roleState.setTextAndPlaceCursorAtEnd(role.customerRole.toString())
                                            onEvent(
                                                CustomerUpdateUiEvent.OnRolesDropdownExpandedChange(
                                                    false,
                                                ),
                                            )
                                        },
                                        contentPadding =
                                            PaddingValues(
                                                horizontal = 16.dp,
                                                vertical = 12.dp,
                                            ),
                                    )
                                }
                            }
                        }
                        OutlinedTextField(
                            state = uiState.nameState,
                            modifier = Modifier.fillMaxWidth(),
                            labelPosition = TextFieldLabelPosition.Attached(true),
                            label = {
                                Text(text = stringResource(R.string.name))
                            },
                            keyboardOptions =
                                KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                ),
                            onKeyboardAction = {
                                keyboardController?.hide()
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            lineLimits = TextFieldLineLimits.SingleLine,
                            shape = MaterialTheme.shapes.medium,
                        )
                        ExposedDropdownMenuBox(
                            expanded = uiState.expandedGenderDropdown,
                            onExpandedChange = {
                                onEvent(CustomerUpdateUiEvent.OnGenderDropdownExpandedChange(it))
                            },
                        ) {
                            OutlinedTextField(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                                state = uiState.genderState,
                                readOnly = true,
                                lineLimits = TextFieldLineLimits.SingleLine,
                                labelPosition = TextFieldLabelPosition.Attached(true),
                                label = {
                                    Text(text = stringResource(R.string.gender))
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.expandedGenderDropdown)
                                },
                                keyboardOptions =
                                    KeyboardOptions(
                                        imeAction = ImeAction.Done,
                                    ),
                                onKeyboardAction = {
                                    focusManager.clearFocus()
                                },
                                shape = MaterialTheme.shapes.medium,
                            )
                            ExposedDropdownMenu(
                                expanded = uiState.expandedGenderDropdown,
                                onDismissRequest = {
                                    onEvent(
                                        CustomerUpdateUiEvent.OnGenderDropdownExpandedChange(
                                            false,
                                        ),
                                    )
                                },
                            ) {
                                uiState.genderList.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = option,
                                                style = MaterialTheme.typography.bodyLarge,
                                            )
                                        },
                                        onClick = {
                                            uiState.genderState.setTextAndPlaceCursorAtEnd(option)
                                            onEvent(
                                                CustomerUpdateUiEvent.OnGenderDropdownExpandedChange(
                                                    false,
                                                ),
                                            )
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }
                            }
                        }
                        OutlinedTextField(
                            state = rememberTextFieldState(phoneNumber),
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            labelPosition = TextFieldLabelPosition.Attached(true),
                            label = {
                                Text(text = stringResource(R.string.phone_number))
                            },
                            prefix = {
                                Text(text = stringResource(R.string._91))
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            outputTransformation = {
                                if (length > 5) insert(5, " ")
                            },
                            lineLimits = TextFieldLineLimits.SingleLine,
                            shape = MaterialTheme.shapes.medium,
                        )
                    }
                }
            }
        },
    )

    ProgressDialog(
        open = uiState.openProgressDialog,
        onDismissRequest = {},
    )
}

@Preview
@Composable
private fun CustomerUpdateScreenPreview() {
    KourierlyTheme {
        CustomerUpdateScreenContent(
            uiState =
                CustomerUpdateUiState(
                    genderState = rememberTextFieldState("Male"),
                    nameState = rememberTextFieldState("Bruce"),
                    roleState = rememberTextFieldState("Sender"),
                    success = true,
                ),
            onEvent = {},
            phoneNumber = "8752415865",
            onNavigateUp = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

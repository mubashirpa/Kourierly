package com.evaluation.kourierly.presentation.cutomerRole

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evaluation.kourierly.R
import com.evaluation.kourierly.presentation.theme.KourierlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerRoleScreen(
    uiState: CustomerRoleUiState,
    onEvent: (CustomerRoleUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackbarHostState.showSnackbar(userMessage)
            // Once the message is displayed and dismissed, notify the ViewModel.
            onEvent(CustomerRoleUiEvent.UserMessageShown)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Select Role",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding(),
                ) {
                    Text(text = stringResource(R.string._continue))
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { innerPadding ->
            when {
                uiState.loading -> {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .wrapContentSize(Alignment.Center),
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.success -> {
                    val options = uiState.roles
                    Column(
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        var expanded by remember { mutableStateOf(false) }
                        val textFieldState =
                            rememberTextFieldState(options[0].customerRole.toString())

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                        ) {
                            OutlinedTextField(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                                state = textFieldState,
                                readOnly = true,
                                lineLimits = TextFieldLineLimits.SingleLine,
                                label = {
                                    Text(text = stringResource(R.string.role))
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
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
                                            textFieldState.setTextAndPlaceCursorAtEnd(role.customerRole.toString())
                                            expanded = false
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
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomerRoleScreenPreview() {
    KourierlyTheme {
        CustomerRoleScreen(
            uiState = CustomerRoleUiState(),
            onEvent = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

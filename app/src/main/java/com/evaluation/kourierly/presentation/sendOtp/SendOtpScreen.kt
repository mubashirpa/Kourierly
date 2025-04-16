package com.evaluation.kourierly.presentation.sendOtp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.evaluation.kourierly.R
import com.evaluation.kourierly.presentation.components.ProgressDialog
import com.evaluation.kourierly.presentation.theme.KourierlyTheme
import kotlinx.coroutines.flow.filter
import org.koin.androidx.compose.koinViewModel

@Composable
fun SendOtpScreen(
    onSendOtpSuccess: (phoneNumber: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SendOtpViewModel = koinViewModel(),
) {
    val currentOnSendOtpSuccess by rememberUpdatedState(onSendOtpSuccess)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(viewModel, lifecycle) {
        snapshotFlow { viewModel.uiState }
            .filter { it.sendOtpSuccess }
            .flowWithLifecycle(lifecycle)
            .collect {
                viewModel.onEvent(SendOtpUiEvent.OnNavigateToVerifyOtp)
                currentOnSendOtpSuccess(it.phoneNumber.text.toString())
            }
    }

    SendOtpScreenContent(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SendOtpScreenContent(
    uiState: SendOtpUiState,
    onEvent: (SendOtpUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackbarHostState.showSnackbar(userMessage)
            // Once the message is displayed and dismissed, notify the ViewModel.
            onEvent(SendOtpUiEvent.UserMessageShown)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.what_s_your_phone_number),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider()
                Row(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                            .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Button(
                        onClick = {
                            onEvent(SendOtpUiEvent.SendOtp(uiState.phoneNumber.text.toString()))
                        },
                        modifier =
                            Modifier
                                .heightIn(min = 56.dp)
                                .weight(1f),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text(
                            text = stringResource(R.string.next),
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                        )
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
            ) {
                OutlinedTextField(
                    state = uiState.phoneNumber,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = stringResource(R.string.phone_number))
                    },
                    prefix = {
                        Text(text = stringResource(R.string._91))
                    },
                    supportingText = {
                        Text(
                            text =
                                stringResource(
                                    R.string.kourierly_will_send_you_a_sms_with_a_verification_code_message_and_data_rates_may_apply,
                                ),
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onKeyboardAction = {
                        focusManager.clearFocus()
                    },
                    inputTransformation =
                        InputTransformation.maxLength(10).then {
                            if (!this.asCharSequence().isDigitsOnly()) {
                                revertAllChanges()
                            }
                        },
                    outputTransformation = {
                        if (length > 5) insert(5, " ")
                    },
                    lineLimits = TextFieldLineLimits.SingleLine,
                    shape = MaterialTheme.shapes.medium,
                )
            }
        },
    )

    ProgressDialog(
        open = uiState.loading,
        onDismissRequest = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SendOtpScreenPreview() {
    KourierlyTheme {
        SendOtpScreenContent(
            uiState = SendOtpUiState(),
            onEvent = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

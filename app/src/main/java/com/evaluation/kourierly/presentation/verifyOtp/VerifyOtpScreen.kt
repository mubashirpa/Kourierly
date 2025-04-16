package com.evaluation.kourierly.presentation.verifyOtp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.evaluation.kourierly.R
import com.evaluation.kourierly.presentation.components.ProgressDialog
import com.evaluation.kourierly.presentation.sendOtp.components.OtpInputField
import com.evaluation.kourierly.presentation.theme.KourierlyTheme
import kotlinx.coroutines.flow.filter
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyOtpScreen(
    phoneNumber: String,
    onVerifyOtpSuccess: (customerId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VerifyOtpViewModel = koinViewModel(),
) {
    val currentOnVerifyOtpSuccess by rememberUpdatedState(onVerifyOtpSuccess)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(viewModel, lifecycle) {
        snapshotFlow { viewModel.uiState }
            .filter { it.verifyOtpSuccess }
            .flowWithLifecycle(lifecycle)
            .collect {
                viewModel.uiState.customerId?.let(currentOnVerifyOtpSuccess)
            }
    }

    VerifyOtpScreenContent(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        phoneNumber = phoneNumber,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerifyOtpScreenContent(
    uiState: VerifyOtpUiState,
    onEvent: (VerifyOtpUiEvent) -> Unit,
    phoneNumber: String,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackbarHostState.showSnackbar(userMessage)
            // Once the message is displayed and dismissed, notify the ViewModel.
            onEvent(VerifyOtpUiEvent.UserMessageShown)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.verify_your_phone_number),
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
                    onClick = {
                        onEvent(VerifyOtpUiEvent.VerifyOtp(phoneNumber, uiState.otpValue))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding(),
                ) {
                    Text(text = stringResource(R.string.done))
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
                Text(
                    text =
                        stringResource(
                            R.string.enter_the_6_digit_code_sent_to_you_at,
                            phoneNumber,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OtpInputField(
                    otpText = uiState.otpValue,
                    onOtpValueChange = { value, otpFilled ->
                        onEvent(VerifyOtpUiEvent.OnOtpValueChange(value, otpFilled))
                        if (otpFilled) {
                            keyboardController?.hide()
                        }
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                    shouldCursorBlink = false,
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
private fun VerifyOtpScreenPreview() {
    KourierlyTheme {
        VerifyOtpScreenContent(
            uiState = VerifyOtpUiState(),
            onEvent = {},
            phoneNumber = "9876543210",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.evaluation.kourierly.R
import com.evaluation.kourierly.presentation.sendOtp.components.OtpInputField
import com.evaluation.kourierly.presentation.theme.KourierlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyOtpScreen(
    phoneNumber: String,
    onVerifyOtpSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var otpValue by remember { mutableStateOf("") }
    var isOtpFilled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

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
                        onVerifyOtpSuccess()
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
                    otpText = otpValue,
                    onOtpValueChange = { value, otpFilled ->
                        otpValue = value
                        isOtpFilled = otpFilled
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
}

@Preview(showBackground = true)
@Composable
private fun VerifyOtpScreenPreview() {
    KourierlyTheme {
        VerifyOtpScreen(
            phoneNumber = "9876543210",
            onVerifyOtpSuccess = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

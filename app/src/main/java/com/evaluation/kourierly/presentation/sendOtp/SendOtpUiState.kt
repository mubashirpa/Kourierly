package com.evaluation.kourierly.presentation.sendOtp

import androidx.compose.foundation.text.input.TextFieldState

data class SendOtpUiState(
    val loading: Boolean = false,
    val phoneNumber: TextFieldState = TextFieldState(),
    val sendOtpSuccess: Boolean = false,
    val userMessage: String? = null,
)

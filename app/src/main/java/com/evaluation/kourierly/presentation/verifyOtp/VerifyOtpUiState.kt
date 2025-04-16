package com.evaluation.kourierly.presentation.verifyOtp

data class VerifyOtpUiState(
    val customerId: String? = null,
    val loading: Boolean = false,
    val otpValue: String = "",
    val otpFilled: Boolean = false,
    val userMessage: String? = null,
    val verifyOtpSuccess: Boolean = false,
)

package com.evaluation.kourierly.presentation.sendOtp

sealed class SendOtpUiEvent {
    data class SendOtp(
        val phoneNumber: String,
    ) : SendOtpUiEvent()

    data object OnNavigateToVerifyOtp : SendOtpUiEvent()

    data object UserMessageShown : SendOtpUiEvent()
}

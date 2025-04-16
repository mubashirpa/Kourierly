package com.evaluation.kourierly.presentation.verifyOtp

sealed class VerifyOtpUiEvent {
    data class OnOtpValueChange(
        val value: String,
        val otpFilled: Boolean,
    ) : VerifyOtpUiEvent()

    data class VerifyOtp(
        val phoneNumber: String,
        val otp: String,
    ) : VerifyOtpUiEvent()

    data object UserMessageShown : VerifyOtpUiEvent()
}

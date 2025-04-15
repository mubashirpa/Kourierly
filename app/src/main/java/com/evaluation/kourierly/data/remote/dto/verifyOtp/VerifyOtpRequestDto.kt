package com.evaluation.kourierly.data.remote.dto.verifyOtp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequestDto(
    val otp: String,
    @SerialName("phone_number")
    val phoneNumber: String,
)

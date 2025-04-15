package com.evaluation.kourierly.data.remote.dto.sendOtp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerSendOtpRequestDto(
    @SerialName("phone_number")
    val phoneNumber: String,
)

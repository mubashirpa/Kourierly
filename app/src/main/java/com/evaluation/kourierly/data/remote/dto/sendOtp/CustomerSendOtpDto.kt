package com.evaluation.kourierly.data.remote.dto.sendOtp

import kotlinx.serialization.Serializable

@Serializable
data class CustomerSendOtpDto(
    val message: String? = null,
    val success: Boolean? = null,
)

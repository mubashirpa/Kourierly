package com.evaluation.kourierly.data.remote.dto.verifyOtp

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpDto(
    val `data`: List<VerifyOtpDataDto>? = null,
    val message: String? = null,
    val success: Boolean? = null,
)

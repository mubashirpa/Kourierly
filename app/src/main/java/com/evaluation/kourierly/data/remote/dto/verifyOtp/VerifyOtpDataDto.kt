package com.evaluation.kourierly.data.remote.dto.verifyOtp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpDataDto(
    val active: Int? = null,
    @SerialName("customer_id")
    val customerId: Int? = null,
    @SerialName("customer_name")
    val customerName: String? = null,
    val gender: String? = null,
    @SerialName("isregister")
    val isRegister: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("role_id")
    val roleId: String? = null,
)

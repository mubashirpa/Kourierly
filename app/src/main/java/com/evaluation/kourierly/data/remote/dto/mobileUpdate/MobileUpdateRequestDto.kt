package com.evaluation.kourierly.data.remote.dto.mobileUpdate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MobileUpdateRequestDto(
    @SerialName("customer_id")
    val customerId: String,
    @SerialName("customer_name")
    val customerName: String,
    val gender: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("role_id")
    val roleId: String,
)

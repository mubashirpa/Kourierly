package com.evaluation.kourierly.data.remote.dto.mobileUpdate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MobileUpdateDataDto(
    val active: Int? = null,
    val address: String? = null,
    @SerialName("city_id")
    val cityId: String? = null,
    val createdAt: String? = null,
    val createdBy: Int? = null,
    @SerialName("customer_id")
    val customerId: Int? = null,
    @SerialName("customer_name")
    val customerName: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val image: String? = null,
    @SerialName("isregister")
    val isRegister: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    val pincode: String? = null,
    @SerialName("role_id")
    val roleId: Int? = null,
    @SerialName("sender_id")
    val senderId: Int? = null,
    @SerialName("state_id")
    val stateId: String? = null,
    val updatedAt: String? = null,
    val updatedBy: Int? = null,
)

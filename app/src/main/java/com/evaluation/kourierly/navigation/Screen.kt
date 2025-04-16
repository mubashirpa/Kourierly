package com.evaluation.kourierly.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object SendOtp : Screen()

    @Serializable
    data class VerifyOtp(
        val phoneNumber: String,
    ) : Screen()

    @Serializable
    data class CustomerRole(
        val phoneNumber: String,
        val customerId: String,
    ) : Screen()

    @Serializable
    data class CustomerUpdate(
        val phoneNumber: String,
        val customerId: String,
        val roleId: String,
    ) : Screen()
}

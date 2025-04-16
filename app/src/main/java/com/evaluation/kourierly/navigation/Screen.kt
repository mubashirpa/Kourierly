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
    data object CustomerRole : Screen()
}

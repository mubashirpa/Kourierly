package com.evaluation.kourierly.presentation.verifyOtp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.kourierly.domain.repository.KourierlyRepository
import kotlinx.coroutines.launch

class VerifyOtpViewModel(
    private val kourierlyRepository: KourierlyRepository,
) : ViewModel() {
    var uiState by mutableStateOf(VerifyOtpUiState())
        private set

    fun onEvent(event: VerifyOtpUiEvent) {
        when (event) {
            is VerifyOtpUiEvent.OnOtpValueChange -> {
                uiState =
                    uiState.copy(
                        otpValue = event.value,
                        otpFilled = event.otpFilled,
                    )
            }

            VerifyOtpUiEvent.UserMessageShown -> {
                uiState = uiState.copy(userMessage = null)
            }

            is VerifyOtpUiEvent.VerifyOtp -> {
                verifyOtp(event.phoneNumber, event.otp)
            }
        }
    }

    private fun verifyOtp(
        phoneNumber: String,
        otp: String,
    ) {
        if (!uiState.otpFilled) {
            uiState = uiState.copy(userMessage = "Invalid OTP")
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                val result = kourierlyRepository.verifyOtp(phoneNumber, otp)
                val success = result.success == true
                uiState =
                    uiState.copy(
                        customerId =
                            result.data
                                ?.firstOrNull()
                                ?.customerId
                                ?.toString(),
                        loading = false,
                        userMessage = if (success) null else result.message,
                        verifyOtpSuccess = success,
                    )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState =
                    uiState.copy(
                        loading = false,
                        userMessage = e.message,
                    )
            }
        }
    }
}

package com.evaluation.kourierly.presentation.sendOtp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.kourierly.domain.repository.KourierlyRepository
import kotlinx.coroutines.launch

class SendOtpViewModel(
    private val kourierlyRepository: KourierlyRepository,
) : ViewModel() {
    var uiState by mutableStateOf(SendOtpUiState())
        private set

    fun onEvent(event: SendOtpUiEvent) {
        when (event) {
            SendOtpUiEvent.OnNavigateToVerifyOtp -> {
                uiState = uiState.copy(sendOtpSuccess = false)
            }

            is SendOtpUiEvent.SendOtp -> {
                sendOtp(event.phoneNumber)
            }

            SendOtpUiEvent.UserMessageShown -> {
                uiState = uiState.copy(userMessage = null)
            }
        }
    }

    private fun sendOtp(phoneNumber: String) {
        if (phoneNumber.length < 10) {
            uiState = uiState.copy(userMessage = "Invalid phone number")
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                val result = kourierlyRepository.customerSendOtp(phoneNumber)
                val success = result.success == true
                uiState =
                    uiState.copy(
                        loading = false,
                        sendOtpSuccess = success,
                        userMessage = if (success) null else result.message,
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

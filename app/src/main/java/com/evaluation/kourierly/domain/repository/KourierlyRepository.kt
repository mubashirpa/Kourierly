package com.evaluation.kourierly.domain.repository

import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateDto
import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateRequestDto
import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleListDto
import com.evaluation.kourierly.data.remote.dto.sendOtp.CustomerSendOtpDto

interface KourierlyRepository {
    suspend fun customerSendOtp(phoneNumber: String): CustomerSendOtpDto

    suspend fun verifyOtp(
        phoneNumber: String,
        otp: String,
    ): CustomerRoleListDto

    suspend fun customerRoleList(): CustomerRoleListDto

    suspend fun mobileUpdate(mobileUpdateRequest: MobileUpdateRequestDto): MobileUpdateDto
}

package com.evaluation.kourierly.domain.repository

import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateDto
import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateRequestDto
import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleListDto
import com.evaluation.kourierly.data.remote.dto.sendOtp.CustomerSendOtpDto
import com.evaluation.kourierly.data.remote.dto.verifyOtp.VerifyOtpRequestDto

interface KourierlyRepository {
    suspend fun customerSendOtp(phoneNumber: String): CustomerSendOtpDto

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequestDto): CustomerRoleListDto

    suspend fun customerRoleList(): CustomerRoleListDto

    suspend fun mobileUpdate(mobileUpdateRequest: MobileUpdateRequestDto): MobileUpdateDto
}

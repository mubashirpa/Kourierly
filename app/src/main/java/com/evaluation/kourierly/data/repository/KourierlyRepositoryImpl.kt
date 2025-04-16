package com.evaluation.kourierly.data.repository

import com.evaluation.kourierly.core.Constants
import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateDto
import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateRequestDto
import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleListDto
import com.evaluation.kourierly.data.remote.dto.sendOtp.CustomerSendOtpDto
import com.evaluation.kourierly.data.remote.dto.sendOtp.CustomerSendOtpRequestDto
import com.evaluation.kourierly.data.remote.dto.verifyOtp.VerifyOtpDto
import com.evaluation.kourierly.data.remote.dto.verifyOtp.VerifyOtpRequestDto
import com.evaluation.kourierly.domain.repository.KourierlyRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType

class KourierlyRepositoryImpl(
    private val httpClient: HttpClient,
) : KourierlyRepository {
    override suspend fun customerSendOtp(phoneNumber: String): CustomerSendOtpDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("customerSendOTP", "mobileCondition")
                }
                contentType(ContentType.Application.Json)
                val sendOtpRequest = CustomerSendOtpRequestDto(phoneNumber = phoneNumber)
                setBody(sendOtpRequest)
            }.body()

    override suspend fun verifyOtp(
        phoneNumber: String,
        otp: String,
    ): VerifyOtpDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("verifyOTP", "mobileCondition")
                }
                contentType(ContentType.Application.Json)
                val verifyOtpRequest =
                    VerifyOtpRequestDto(
                        phoneNumber = phoneNumber,
                        otp = otp,
                    )
                setBody(verifyOtpRequest)
            }.body()

    override suspend fun customerRoleList(): CustomerRoleListDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("customerRoleList", "mobileCondition")
                }
            }.body()

    override suspend fun mobileUpdate(
        customerId: String,
        customerName: String,
        gender: String,
        phoneNumber: String,
        roleId: String,
    ): MobileUpdateDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("customerUpdate", "mobileUpdate")
                }
                contentType(ContentType.Application.Json)
                val mobileUpdateRequest =
                    MobileUpdateRequestDto(
                        customerId = customerId,
                        customerName = customerName,
                        gender = gender,
                        phoneNumber = phoneNumber,
                        roleId = roleId,
                    )
                setBody(mobileUpdateRequest)
            }.body()
}

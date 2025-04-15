package com.evaluation.kourierly.data.repository

import com.evaluation.kourierly.core.Constants
import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateDto
import com.evaluation.kourierly.data.remote.dto.mobileUpdate.MobileUpdateRequestDto
import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleListDto
import com.evaluation.kourierly.data.remote.dto.sendOtp.CustomerSendOtpDto
import com.evaluation.kourierly.data.remote.dto.sendOtp.CustomerSendOtpRequestDto
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
    override suspend fun customerSendOtp(sendOtpRequest: CustomerSendOtpRequestDto): CustomerSendOtpDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("customerSendOTP", "mobileCondition")
                }
                contentType(ContentType.Application.Json)
                setBody(sendOtpRequest)
            }.body()

    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequestDto): CustomerRoleListDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("verifyOTP", "mobileCondition")
                }
                contentType(ContentType.Application.Json)
                setBody(verifyOtpRequest)
            }.body()

    override suspend fun customerRoleList(): CustomerRoleListDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("customerRoleList", "mobileCondition")
                }
            }.body()

    override suspend fun mobileUpdate(mobileUpdateRequest: MobileUpdateRequestDto): MobileUpdateDto =
        httpClient
            .post(Constants.KOURIERLY_SERVICE_BASE_URL) {
                url {
                    appendPathSegments("customerUpdate", "mobileUpdate")
                }
                contentType(ContentType.Application.Json)
                setBody(mobileUpdateRequest)
            }.body()
}

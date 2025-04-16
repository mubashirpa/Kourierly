package com.evaluation.kourierly.di

import com.evaluation.kourierly.data.repository.KourierlyRepositoryImpl
import com.evaluation.kourierly.domain.repository.KourierlyRepository
import com.evaluation.kourierly.presentation.sendOtp.SendOtpViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        singleOf(::KourierlyRepositoryImpl) { bind<KourierlyRepository>() }
        viewModelOf(::SendOtpViewModel)
        single {
            HttpClient {
                expectSuccess = true
                install(ContentNegotiation) {
                    json(
                        Json {
                            isLenient = true
                            ignoreUnknownKeys = true
                            useAlternativeNames = false
                        },
                    )
                }
            }
        }
    }

package com.evaluation.kourierly

import android.app.Application
import com.evaluation.kourierly.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class KourierlyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KourierlyApplication)
            modules(appModule)
        }
    }
}

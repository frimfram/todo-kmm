package com.example.kmmtodoapp.android

import android.app.Application
import com.example.kmmtodoapp.android.di.appModule
import com.example.kmmtodoapp.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        /*
        initKoin {
            androidLogger()
            androidContext(this@TodoApplication)
            modules(appModule)
        }
         */
    }
}
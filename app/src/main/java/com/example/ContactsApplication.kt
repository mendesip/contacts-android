package com.example

import android.app.Application
import com.example.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactsApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@ContactsApplication)
            modules(appModule)
        }
    }
}
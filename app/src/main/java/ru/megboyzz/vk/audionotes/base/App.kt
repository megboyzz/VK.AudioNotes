package ru.megboyzz.vk.audionotes.base

import android.app.Application
import ru.megboyzz.vk.audionotes.ui.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(homeModule)
        }
    }
}
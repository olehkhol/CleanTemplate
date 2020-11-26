package ua.`in`.khol.oleh.githobbit

import android.app.Application
import ua.`in`.khol.oleh.githobbit.di.ApplicationInjector

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ApplicationInjector.init()
    }
}
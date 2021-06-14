package ua.`in`.khol.oleh.githobbit

import android.app.Application
import timber.log.Timber
import ua.`in`.khol.oleh.githobbit.di.ApplicationComponent
import ua.`in`.khol.oleh.githobbit.di.DaggerApplicationComponent

class MainApplication : Application() {

    val daggerComponent: ApplicationComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
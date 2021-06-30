package ua.`in`.khol.oleh.githobbit

import android.app.Application
import timber.log.Timber
import ua.`in`.khol.oleh.githobbit.di.ApplicationComponent
import ua.`in`.khol.oleh.githobbit.di.ApplicationModule
import ua.`in`.khol.oleh.githobbit.di.DaggerApplicationComponent

class MainApplication : Application() {

    lateinit var daggerComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        Timber.plant(Timber.DebugTree())
    }
}
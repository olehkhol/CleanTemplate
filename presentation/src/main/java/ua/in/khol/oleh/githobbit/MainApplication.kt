package ua.`in`.khol.oleh.githobbit

import android.app.Application
import ua.`in`.khol.oleh.githobbit.di.ApplicationComponent
import ua.`in`.khol.oleh.githobbit.di.DaggerApplicationComponent

class MainApplication : Application() {

    val daggerComponent: ApplicationComponent = DaggerApplicationComponent.create()
}
package ua.`in`.khol.oleh.githobbit.di

import dagger.Component
import ua.`in`.khol.oleh.githobbit.view.MainView
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(activity: MainView)
}
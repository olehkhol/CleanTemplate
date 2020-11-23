package ua.`in`.khol.oleh.githobbit.di

import dagger.Component
import ua.`in`.khol.oleh.githobbit.presentation.view.DetailView
import ua.`in`.khol.oleh.githobbit.presentation.view.MainView
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(view: MainView)
    fun inject(view: DetailView)
}
package ua.`in`.khol.oleh.githobbit.di

import dagger.Component
import ua.`in`.khol.oleh.githobbit.data.di.NetworkModule
import ua.`in`.khol.oleh.githobbit.data.di.RepositoryModule
import ua.`in`.khol.oleh.githobbit.domain.di.UseCaseModule
import ua.`in`.khol.oleh.githobbit.view.DetailView
import ua.`in`.khol.oleh.githobbit.view.MainView
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        NetworkModule::class]
)
interface ApplicationComponent {

    fun inject(view: MainView)
    fun inject(view: DetailView)
}
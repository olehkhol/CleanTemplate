package ua.`in`.khol.oleh.githobbit.di

import dagger.Component
import ua.`in`.khol.oleh.githobbit.data.di.NetworkModule
import ua.`in`.khol.oleh.githobbit.data.paging.RepositoryDataSource
import ua.`in`.khol.oleh.githobbit.data.paging.SubscriberDataSource
import ua.`in`.khol.oleh.githobbit.view.DetailView
import ua.`in`.khol.oleh.githobbit.view.MainView
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(view: MainView)
    fun inject(view: DetailView)
    fun inject(repositoryDataSource: RepositoryDataSource)
    fun inject(subscriberDataSource: SubscriberDataSource)
}
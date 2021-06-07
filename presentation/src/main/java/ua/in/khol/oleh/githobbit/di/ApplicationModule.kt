package ua.`in`.khol.oleh.githobbit.di

import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetRepos
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetSubs
import ua.`in`.khol.oleh.githobbit.viewmodel.ViewModelFactory
import javax.inject.Singleton


@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideGitMapper() = GitMapper

    @Provides
    @Singleton
    fun provideViewModelFactory(getRepos: GetRepos, getSubs: GetSubs) =
        ViewModelFactory(getRepos, getSubs)
}
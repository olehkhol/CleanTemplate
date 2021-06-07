package ua.`in`.khol.oleh.githobbit.domain.di

import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetRepos
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetSubs
import ua.`in`.khol.oleh.githobbit.domain.usecase.implementation.GetReposImpl
import ua.`in`.khol.oleh.githobbit.domain.usecase.implementation.GetSubsImpl
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetRepos(repository: GitRepository): GetRepos =
        GetReposImpl(repository)

    @Singleton
    @Provides
    fun provideGetSubs(repository: GitRepository): GetSubs =
        GetSubsImpl(repository)
}
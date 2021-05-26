package ua.`in`.khol.oleh.githobbit.domain.di

import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import ua.`in`.khol.oleh.githobbit.domain.usecase.GetRepos
import ua.`in`.khol.oleh.githobbit.domain.usecase.GetSubs
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetRepos(repository: GitRepository) = GetRepos(repository)

    @Singleton
    @Provides
    fun provideGetSubs(repository: GitRepository) = GetSubs(repository)

}
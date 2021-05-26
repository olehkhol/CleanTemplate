package ua.`in`.khol.oleh.githobbit.data.di

import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.repository.implementation.GitRepositoryImpl
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    // We need to define a return type explicitly to avoid injection error
    fun provideGitRepository(gitService: GitService): GitRepository =
        GitRepositoryImpl(gitService)
}
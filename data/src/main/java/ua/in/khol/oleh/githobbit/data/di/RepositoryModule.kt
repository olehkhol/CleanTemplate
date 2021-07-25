package ua.`in`.khol.oleh.githobbit.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.repository.implementation.GitRepositoryImpl
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    // We need to define a return type explicitly to avoid injection error
    fun provideGitRepository(gitService: GitService, gitDatabase: GitDatabase): GitRepository =
        GitRepositoryImpl(gitService, gitDatabase)
}
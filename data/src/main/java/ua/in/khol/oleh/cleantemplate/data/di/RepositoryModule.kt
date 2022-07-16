package ua.`in`.khol.oleh.cleantemplate.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.`in`.khol.oleh.cleantemplate.data.database.GitDatabase
import ua.`in`.khol.oleh.cleantemplate.data.network.github.GitService
import ua.`in`.khol.oleh.cleantemplate.data.repository.implementation.GitRepositoryImpl
import ua.`in`.khol.oleh.cleantemplate.domain.repository.contract.GitRepository
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
package ua.`in`.khol.oleh.githobbit.di

import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.data.github.GitRepository
import ua.`in`.khol.oleh.githobbit.data.github.GitRetrofit
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.ViewModelProviderFactory
import javax.inject.Singleton


@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideGitRetrofit(): GitRetrofit {
        return GitRetrofit
    }

    @Singleton
    @Provides
    fun provideGitHelper(gitRetrofit: GitRetrofit): GitRepository {
        return GitRepository(gitRetrofit)
    }

    @Provides
    @Singleton
    fun provideViewModelProviderFactory(gitRepository: GitRepository): ViewModelProviderFactory {
        return ViewModelProviderFactory(gitRepository)
    }
}
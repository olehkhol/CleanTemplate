package ua.`in`.khol.oleh.githobbit.di

import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.github.GitHelper
import ua.`in`.khol.oleh.githobbit.github.GitRetrofit
import ua.`in`.khol.oleh.githobbit.model.GodRepository
import ua.`in`.khol.oleh.githobbit.viewmodel.ViewModelProviderFactory
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
    fun provideGitHelper(gitRetrofit: GitRetrofit): GitHelper {
        return GitHelper(gitRetrofit)
    }

    @Singleton
    @Provides
    fun provideGodRepository(gitHelper: GitHelper): GodRepository {
        return GodRepository(gitHelper)
    }

    @Provides
    @Singleton
    fun provideViewModelProviderFactory(godRepository: GodRepository): ViewModelProviderFactory {
        return ViewModelProviderFactory(godRepository)
    }
}
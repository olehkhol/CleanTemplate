package ua.`in`.khol.oleh.githobbit.di

import ua.`in`.khol.oleh.githobbit.data.github.GitRepositoryImpl
import ua.`in`.khol.oleh.githobbit.data.github.GitRetrofit
import ua.`in`.khol.oleh.githobbit.data.mappers.GitMapper
import ua.`in`.khol.oleh.githobbit.data.paging.RepositoryDataSourceFactory
import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.domain.repositories.GitRepository
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
    fun provideGitMapper(): GitMapper {
        return GitMapper()
    }

//    @Provides
//    fun provideRepositoryDataSource(gitRetrofit: GitRetrofit): RepositoryDataSource {
//        return RepositoryDataSource(gitRepository = GitRepositoryImpl(gitRetrofit, GitMapper()))
//    }

    @Singleton
    @Provides
    fun provideGitRepository(gitRetrofit: GitRetrofit, gitMapper: GitMapper): GitRepository {
        return GitRepositoryImpl(gitRetrofit, gitMapper)
    }

    @Singleton
    @Provides
    fun provideRepositoryDataSourceFactory(gitRepository: GitRepository): RepositoryDataSourceFactory {
        return RepositoryDataSourceFactory(gitRepository)
    }

    @Provides
    @Singleton
    fun provideViewModelProviderFactory(repositoryDataSourceFactory: RepositoryDataSourceFactory): ViewModelProviderFactory {
        return ViewModelProviderFactory(repositoryDataSourceFactory)
    }
}
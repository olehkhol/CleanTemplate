package ua.`in`.khol.oleh.githobbit.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ua.`in`.khol.oleh.githobbit.data.network.github.GitRetrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideGitRetrofit(okHttpClient: OkHttpClient) =
        GitRetrofit(okHttpClient)

    @Singleton
    @Provides
    fun provideGirService(gitRetrofit: GitRetrofit) =
        gitRetrofit.service
}
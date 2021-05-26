package ua.`in`.khol.oleh.githobbit.data.di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ua.`in`.khol.oleh.githobbit.data.network.github.GitRetrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideGitRetrofit(okHttpClient: OkHttpClient) =
        GitRetrofit(okHttpClient)

    @Singleton
    @Provides
    fun provideGirService(gitRetrofit: GitRetrofit) =
        gitRetrofit.service
}
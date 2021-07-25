package ua.`in`.khol.oleh.githobbit.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        GitDatabase.getInstance(context, false)
}
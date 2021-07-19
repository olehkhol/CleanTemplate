package ua.`in`.khol.oleh.githobbit.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context) = GitDatabase.getInstance(context, false)
}
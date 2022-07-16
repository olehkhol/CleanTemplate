package ua.`in`.khol.oleh.cleantemplate.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.`in`.khol.oleh.cleantemplate.data.database.ObjectBox
import ua.`in`.khol.oleh.cleantemplate.data.database.GitDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        GitDatabase.getInstance(context, false)

    @Singleton
    @Provides
    fun provideObjectBoxStore(@ApplicationContext context: Context) =
        ObjectBox.init(context)
}
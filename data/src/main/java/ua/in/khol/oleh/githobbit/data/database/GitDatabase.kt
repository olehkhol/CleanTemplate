package ua.`in`.khol.oleh.githobbit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ua.`in`.khol.oleh.githobbit.data.database.dao.RemoteKeysDao
import ua.`in`.khol.oleh.githobbit.data.database.dao.RepoDao
import ua.`in`.khol.oleh.githobbit.data.database.entity.RemoteKeysEntity
import ua.`in`.khol.oleh.githobbit.data.database.entity.RepoEntity

@Database(
    entities = [RepoEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GitDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: GitDatabase? = null

        fun getInstance(context: Context, useInMemory: Boolean) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, useInMemory)
                    .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context, useInMemory: Boolean): GitDatabase {
            val databaseBuilder = if (useInMemory)
                Room.inMemoryDatabaseBuilder(context, GitDatabase::class.java)
            else
                Room.databaseBuilder(context, GitDatabase::class.java, "Hobbit.db")

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
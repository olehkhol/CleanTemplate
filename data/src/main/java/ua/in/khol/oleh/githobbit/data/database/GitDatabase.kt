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
    entities = [RepoEntity::class,
        RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GitDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: GitDatabase? = null

        fun getInstance(context: Context): GitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
                    .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): GitDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                GitDatabase::class.java,
                "Hobbit.db"
            ).build()
    }
}
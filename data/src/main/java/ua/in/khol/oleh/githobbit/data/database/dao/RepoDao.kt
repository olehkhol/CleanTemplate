package ua.`in`.khol.oleh.githobbit.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.`in`.khol.oleh.githobbit.data.database.entity.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM repo WHERE name LIKE :query ORDER BY stars DESC")
    fun reposByName(query: String): PagingSource<Int, RepoEntity>

    @Query("DELETE FROM repo")
    suspend fun clearRepos()
}
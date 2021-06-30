package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase
import ua.`in`.khol.oleh.githobbit.data.database.entity.RemoteKeysEntity
import ua.`in`.khol.oleh.githobbit.data.database.entity.RepoEntity
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepoItem
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import java.io.IOException

@ExperimentalPagingApi
class GitRemoteMediator(
    private val query: String,
    private val database: GitDatabase,
    private val service: GitService
) : RemoteMediator<Int, Repo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {

        val page: Int = when (loadType) {
            REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GitService.START_PAGE
            }
            PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                prevKey
            }
            APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                nextKey
            }
        }

        val apiQuery = query

        try {
            val apiResponse = service.searchRepos(apiQuery, page, state.config.pageSize)

            val repoItems: List<RepoItem> = apiResponse.repoItems
            val endOfPaginationReached = repoItems.isEmpty()

            database.withTransaction {
                // clear all tables in the database
                if (loadType == REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.repoDao().clearRepos()
                }
                val prevKey = if (page == GitService.START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeysEntities = mutableListOf<RemoteKeysEntity>()
                val repoEntities = mutableListOf<RepoEntity>()
                repoItems.forEach { repoItem ->
                    remoteKeysEntities.add(RemoteKeysEntity(repoItem.id, prevKey, nextKey))
                    repoEntities.add(GitMapper.asRepoEntity(repoItem))
                }
                database.remoteKeysDao().insertAll(remoteKeysEntities)
                database.repoDao().insertAll(repoEntities)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            // IOException for network failures.
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Repo>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        val pages: List<Page<Int, Repo>> = state.pages
        val page = pages.lastOrNull(predicate = { page: Page<Int, Repo> ->
            val data: List<Repo> = page.data
            data.isNotEmpty()
        })
        // From that last page, get the last item
        val repo: Repo? = page?.data?.lastOrNull()
        // Get the remoteKeys of the last item retrieved
        return repo?.let { database.remoteKeysDao().remoteKeysRepoId(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Repo>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        val pages: List<Page<Int, Repo>> = state.pages
        val page = pages.firstOrNull(predicate = { page: Page<Int, Repo> ->
            val data: List<Repo> = page.data
            data.isNotEmpty()
        })
        // From that first page, get the first item
        val repo: Repo? = page?.data?.firstOrNull()
        // Get the remote keys of the first items retrieved
        return repo?.let { database.remoteKeysDao().remoteKeysRepoId(it.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Repo>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        val position: Int? = state.anchorPosition
        // Get the item closest to the anchor position
        val repo: Repo? = position?.let { state.closestItemToPosition(it) }
        // Get the remote keys of the item retrieved
        return repo?.let { database.remoteKeysDao().remoteKeysRepoId(it.id) }
    }
}

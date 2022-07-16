package ua.`in`.khol.oleh.cleantemplate.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import timber.log.Timber
import ua.`in`.khol.oleh.cleantemplate.data.database.GitDatabase
import ua.`in`.khol.oleh.cleantemplate.data.database.entity.RemoteKeysEntity
import ua.`in`.khol.oleh.cleantemplate.data.database.entity.RepoEntity
import ua.`in`.khol.oleh.cleantemplate.data.mapper.GitMapper
import ua.`in`.khol.oleh.cleantemplate.data.network.github.GitService
import java.io.IOException

@ExperimentalPagingApi
class GitRemoteMediator(
    private val query: String,
    private val service: GitService,
    private val database: GitDatabase
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {

        val page: Int = when (loadType) {
            REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val currentKey = remoteKeys?.nextKey?.minus(1) ?: GitService.START_PAGE
                Timber.d("currentKey = $currentKey")
                currentKey
            }
            PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                Timber.d("prevKey = $prevKey")
                prevKey
            }
            APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                Timber.d("nextKey = $nextKey")
                nextKey
            }
        }
        Timber.d("Page = $page")
        try {
            val apiResponse = service.searchRepos(query, page, state.config.pageSize)

            val repoItems = apiResponse.repoItems
            val endOfPaginationReached = repoItems.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.repoDao().clearRepos()
                }
                val prevKey = if (page == GitService.START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = mutableListOf<RemoteKeysEntity>()
                val repos = mutableListOf<RepoEntity>()
                repoItems.forEach { repoItem ->
                    keys.add(RemoteKeysEntity(repoItem.id, prevKey, nextKey))
                    repos.add(GitMapper.asRepoEntity(repoItem))
                }
                database.remoteKeysDao().insertAll(keys)
                database.repoDao().insertAll(repos)
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepoEntity>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        val pages = state.pages
        val page = pages.lastOrNull(predicate = { page: Page<Int, RepoEntity> ->
            val data: List<RepoEntity> = page.data
            data.isNotEmpty()
        })
        // From that last page, get the last item
        val repoEntity: RepoEntity? = page?.data?.lastOrNull()
        // Get the remoteKeys of the last item retrieved
        return repoEntity?.let { repo ->
            database.remoteKeysDao().remoteKeysRepoId(repo.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepoEntity>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        val pages: List<Page<Int, RepoEntity>> = state.pages
        val page = pages.firstOrNull(predicate = { page: Page<Int, RepoEntity> ->
            val data: List<RepoEntity> = page.data
            data.isNotEmpty()
        })
        // From that first page, get the first item
        val repoEntity: RepoEntity? = page?.data?.firstOrNull()
        // Get the remote keys of the first items retrieved
        return repoEntity?.let { repo ->
            database.remoteKeysDao().remoteKeysRepoId(repo.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepoEntity>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        val position: Int? = state.anchorPosition
        // Get the item closest to the anchor position
        val repoEntity: RepoEntity? = position?.let { state.closestItemToPosition(it) }
        // Get the remote keys of the item retrieved
        return repoEntity?.id?.let { repoId ->
            database.remoteKeysDao().remoteKeysRepoId(repoId)
        }
    }
}

package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import timber.log.Timber
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import java.io.IOException

class DatabasePagingSource(
    private val query: String,
    private val database: GitDatabase
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val nextPageNumber = params.key ?: 1

        try {
            val response = database.repoDao().reposByName(query, nextPageNumber, 50)

            //searchRepos(query, nextPageNumber, params.loadSize)
            val repos = response
                .map { repoEntity ->
                    GitMapper.asRepo(repoEntity)
                }
            val prevKey = null
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / GitService.PAGE_SIZE)
            }

            return LoadResult.Page(
                data = repos,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }.also {
            Timber.i("Refresh key is $it")
        }
}
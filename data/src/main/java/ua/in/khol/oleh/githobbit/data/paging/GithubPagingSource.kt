package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.repository.implementation.GitRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import java.io.IOException

private const val START_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GitService,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> =
        try {
            val nextPageNumber = params.key ?: START_PAGE_INDEX
            val apiQuery = query
            val response = service.searchRepos(apiQuery, nextPageNumber, params.loadSize)
            val repos = response.items
                .map { GitMapper.asRepo(it) }  // TODO move mapper to some proper place
            val nextKey = if (repos.isEmpty())
                null
            else
                nextPageNumber + (params.loadSize / NETWORK_PAGE_SIZE)

            LoadResult.Page(
                data = repos,
                prevKey = if (nextPageNumber == START_PAGE_INDEX) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
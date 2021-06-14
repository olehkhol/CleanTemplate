package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import timber.log.Timber
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import java.io.IOException

class GithubPagingSource(
    private val service: GitService,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> =
        try {
            val nextPageNumber = params.key ?: GitService.START_PAGE
            val response = service.searchRepos(query, nextPageNumber, params.loadSize)
            val repos = response.items
                .map { repositoryItem ->
                    GitMapper.asRepo(repositoryItem)
                }
            val prevKey = if (nextPageNumber == GitService.START_PAGE) {
                null
            } else {
                nextPageNumber - 1
            }
            Timber.i("Previous key is $prevKey")

            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / GitService.PAGE_SIZE)
            }
            Timber.i("Next key is $nextKey")

            LoadResult.Page(
                data = repos,
                prevKey = prevKey,
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
        }.also {
            Timber.i("Refresh key is $it")
        }
}
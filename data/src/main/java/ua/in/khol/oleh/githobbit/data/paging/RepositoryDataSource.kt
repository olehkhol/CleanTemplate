package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.domain.models.Repository
import ua.`in`.khol.oleh.githobbit.domain.repositories.GitRepository
import javax.inject.Inject

class RepositoryDataSource @Inject constructor(
    private var query: String = "android",
    private val gitRepository: GitRepository
) : PageKeyedDataSource<Int, Repository>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        if (query.isNotBlank())
            GlobalScope.launch {
                val repos = gitRepository.search(query, 1, params.requestedLoadSize)

                callback.onResult(repos, null, 2)
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        GlobalScope.launch {
            val page = params.key
            val repos = gitRepository.search(query, page, params.requestedLoadSize)

            callback.onResult(repos, page + 1)
        }
    }

    fun refresh(query: String) {
        this.query = query
        invalidate()
    }

}
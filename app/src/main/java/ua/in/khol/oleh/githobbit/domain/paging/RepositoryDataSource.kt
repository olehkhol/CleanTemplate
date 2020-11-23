package ua.`in`.khol.oleh.githobbit.domain.paging

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.data.github.GitRepository
import ua.`in`.khol.oleh.githobbit.domain.Repository

class RepositoryDataSource(
    private val query: String,
    private val gitRepository: GitRepository,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, Repository>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        if (query.isNotBlank())
            coroutineScope.launch {
                val repos = gitRepository.search(query, 1, params.requestedLoadSize)
                    .map {
                        Repository(
                            id = it.id,
                            ownerName = it.owner.login,
                            ownerImage = it.owner.avatar_url,
                            repoName = it.name,
                            repoDescription = it.description,
                            starsCount = it.stargazers_count,
                            forksCount = it.forks_count
                        )
                    }

                callback.onResult(repos, null, 2)
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        coroutineScope.launch {
            val page = params.key
            val repos = gitRepository.search(query, page, params.requestedLoadSize)
                .map {
                    Repository(
                        id = it.id,
                        ownerName = it.owner.login,
                        ownerImage = it.owner.avatar_url,
                        repoName = it.name,
                        repoDescription = it.description,
                        starsCount = it.stargazers_count,
                        forksCount = it.forks_count
                    )
                }

            callback.onResult(repos, page + 1)
        }
    }

    fun refresh() {
        invalidate()
    }

}
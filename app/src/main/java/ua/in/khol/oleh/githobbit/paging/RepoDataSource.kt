package ua.`in`.khol.oleh.githobbit.paging

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.github.GitRepository

class RepoDataSource(
    private val query: String,
    private val gitRepository: GitRepository,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, Repo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        if (query.isNotBlank())
            coroutineScope.launch {
                val repos = gitRepository.search(query, 1, params.requestedLoadSize)
                    .map {
                        Repo(
                            ownerName = it.owner.login,
                            ownerImage = it.owner.avatar_url,
                            repoName = it.name,
                            starsCount = it.stargazers_count
                        )
                    }

                callback.onResult(repos, null, 2)
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        coroutineScope.launch {
            val page = params.key
            val repos = gitRepository.search(query, page, params.requestedLoadSize)
                .map {
                    Repo(
                        ownerName = it.owner.login,
                        ownerImage = it.owner.avatar_url,
                        repoName = it.name,
                        starsCount = it.stargazers_count
                    )
                }

            callback.onResult(repos, page + 1)
        }
    }

    fun refresh() {
        invalidate()
    }

}
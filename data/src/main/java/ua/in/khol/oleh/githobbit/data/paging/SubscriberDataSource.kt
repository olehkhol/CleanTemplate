package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.domain.models.Subscriber
import ua.`in`.khol.oleh.githobbit.domain.repositories.GitRepository
import javax.inject.Inject

class SubscriberDataSource(
    private val owner: String,
    private val repo: String,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, Subscriber>() {

    @Inject
    lateinit var gitRepository: GitRepository

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Subscriber>
    ) {

        if (owner.isNotBlank() && repo.isNotBlank())
            coroutineScope.launch {
                val subscribers = gitRepository.get(owner, repo, 1, params.requestedLoadSize)

                callback.onResult(subscribers, null, 2)
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Subscriber>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Subscriber>) {
        if (owner.isNotBlank() && repo.isNotBlank())
            coroutineScope.launch {
                val page = params.key
                val subscribers = gitRepository.get(owner, repo, page, params.requestedLoadSize)

                callback.onResult(subscribers, page + 1)
            }
    }

    fun refresh() {
        invalidate()
    }

}
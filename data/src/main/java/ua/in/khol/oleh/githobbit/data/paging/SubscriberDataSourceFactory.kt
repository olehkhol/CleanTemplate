package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ua.`in`.khol.oleh.githobbit.domain.entity.Subscriber
import javax.inject.Inject

class SubscriberDataSourceFactory @Inject constructor(
    private var owner: String = "",
    private var repo: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Subscriber>() {

    private lateinit var sourceLiveData: SubscriberDataSource

    override fun create(): DataSource<Int, Subscriber> {
        val subscribeDataSource = SubscriberDataSource(owner, repo, scope)
        sourceLiveData = subscribeDataSource

        return subscribeDataSource
    }

    fun setOwnerAndRepo(owner: String, repo: String) {
        this.owner = owner
        this.repo = repo

        sourceLiveData.refresh()
    }
}
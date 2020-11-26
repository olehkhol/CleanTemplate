package ua.`in`.khol.oleh.githobbit.domain.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ua.`in`.khol.oleh.githobbit.domain.Subscriber

class SubscriberDataSourceFactory(
    private var owner: String = "",
    private var repo: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Subscriber>() {

    private val sourceLiveData = MutableLiveData<SubscriberDataSource>()

    override fun create(): DataSource<Int, Subscriber> {
        val subscribeDataSource = SubscriberDataSource(owner, repo, scope)
        sourceLiveData.postValue(subscribeDataSource)

        return subscribeDataSource
    }

    fun setOwnerAndRepo(owner: String, repo: String) {
        this.owner = owner
        this.repo = repo

        sourceLiveData.value?.refresh()
    }
}
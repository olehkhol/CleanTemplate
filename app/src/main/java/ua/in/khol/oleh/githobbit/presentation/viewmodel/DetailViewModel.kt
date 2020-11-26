package ua.`in`.khol.oleh.githobbit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ua.`in`.khol.oleh.githobbit.domain.Subscriber
import ua.`in`.khol.oleh.githobbit.domain.paging.SubscriberDataSourceFactory
import javax.inject.Inject

class DetailViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 10
    }

    private val dataSourceFactory =
        SubscriberDataSourceFactory(scope = viewModelScope)

    val subscriberList: LiveData<PagedList<Subscriber>> =
        LivePagedListBuilder(dataSourceFactory, pagedListConfig()).build()

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(3 * PAGE_SIZE)
        .setPrefetchDistance(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

    fun getSubscribers(owner: String, repo: String) {
        dataSourceFactory.setOwnerAndRepo(owner, repo)
    }
}

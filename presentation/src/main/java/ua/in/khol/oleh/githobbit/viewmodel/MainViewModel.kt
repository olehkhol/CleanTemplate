package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.data.paging.RepositoryDataSourceFactory
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dataSourceFactory: RepositoryDataSourceFactory
) : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 5
    }

    private val config = PagedList.Config.Builder()
        .setPrefetchDistance(3 * PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

    val repositories: LiveData<PagedList<Repository>> =
        LivePagedListBuilder(dataSourceFactory, config).build()

    fun searchRepos(query: String) {
        dataSourceFactory.setQuery(query)
    }
}
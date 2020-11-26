package ua.`in`.khol.oleh.githobbit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ua.`in`.khol.oleh.githobbit.domain.Repository
import ua.`in`.khol.oleh.githobbit.domain.paging.RepositoryDataSourceFactory
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 5
    }

    private val dataSourceFactory =
        RepositoryDataSourceFactory(scope = viewModelScope)

    val repositoryList: LiveData<PagedList<Repository>> =
        LivePagedListBuilder(dataSourceFactory, pagedListConfig()).build()

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(3 * PAGE_SIZE)
        .setPrefetchDistance(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

    fun searchRepos(query: String) {
        dataSourceFactory.setQuery(query)
    }
}
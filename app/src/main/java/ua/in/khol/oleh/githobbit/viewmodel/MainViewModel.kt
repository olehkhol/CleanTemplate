package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.github.GitRepository
import ua.`in`.khol.oleh.githobbit.paging.RepoDataSourceFactory
import javax.inject.Inject

class MainViewModel @Inject constructor(gitRepository: GitRepository) : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 20
    }

    private val repoSourceFactory =
        RepoDataSourceFactory(repository = gitRepository, scope = viewModelScope)
    val repoList: LiveData<PagedList<Repo>> =
        LivePagedListBuilder(repoSourceFactory, pagedListConfig()).build()

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setPrefetchDistance(3 * PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

    fun searchRepos(query: String) {
        repoSourceFactory.setQuery(query)
    }
}
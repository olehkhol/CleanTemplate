package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.domain.usecase.GetRepos

class MainViewModel(private val getRepos: GetRepos) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(query: String): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult

        if (query == currentQueryValue && lastResult != null) return lastResult
        currentQueryValue = query

        val newResult = getRepos.getSearchResultStream(query)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }
}
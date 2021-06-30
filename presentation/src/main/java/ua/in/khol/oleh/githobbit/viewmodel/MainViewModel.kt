package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetRepos

class MainViewModel(private val getRepos: GetRepos) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    suspend fun searchRepo(query: String): Flow<PagingData<Repo>> {
        val lastResult: Flow<PagingData<Repo>>? = currentSearchResult

        if (query == currentQueryValue && lastResult != null)
            return lastResult

        currentQueryValue = query

        val newResult: Flow<PagingData<Repo>> = getRepos.getSearchResultStream(query)
            .cachedIn(viewModelScope)
//        val newResult: Flow<PagingData<Repo>> = getRepos.getSearchResultStream(query)
//            .cachedIn(viewModelScope)


        currentSearchResult = newResult

        return newResult
    }
}
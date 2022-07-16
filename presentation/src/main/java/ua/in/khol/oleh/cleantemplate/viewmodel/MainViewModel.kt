package ua.`in`.khol.oleh.cleantemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo
import ua.`in`.khol.oleh.cleantemplate.domain.usecase.GetReposUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getRepos: GetReposUseCase) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(query: String): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null)
            return lastResult

        currentQueryValue = query

        val newResult: Flow<PagingData<Repo>> = getRepos.getSearchResultStream(query)
            .cachedIn(viewModelScope)

        currentSearchResult = newResult

        return newResult
    }
}
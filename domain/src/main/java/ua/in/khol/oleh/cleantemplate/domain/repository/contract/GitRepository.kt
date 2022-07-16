package ua.`in`.khol.oleh.cleantemplate.domain.repository.contract

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo

interface GitRepository {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>>
}
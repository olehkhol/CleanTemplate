package ua.`in`.khol.oleh.githobbit.domain.repository.contract

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo

interface GitRepository {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>>
}
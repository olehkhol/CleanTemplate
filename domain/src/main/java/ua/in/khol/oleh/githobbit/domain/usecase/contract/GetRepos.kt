package ua.`in`.khol.oleh.githobbit.domain.usecase.contract

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.domain.model.Repo

interface GetRepos {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>>
}

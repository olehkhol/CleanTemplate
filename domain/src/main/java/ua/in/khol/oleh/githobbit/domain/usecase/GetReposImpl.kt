package ua.`in`.khol.oleh.githobbit.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Inject

class GetReposImpl @Inject constructor(private val repository: GitRepository) {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> =
        repository.getSearchResultStream(query)
}
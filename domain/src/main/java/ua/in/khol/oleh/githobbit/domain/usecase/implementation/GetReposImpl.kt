package ua.`in`.khol.oleh.githobbit.domain.usecase.implementation

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetRepos

class GetReposImpl(private val repository: GitRepository) : GetRepos {

    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> =
        repository.getSearchResultStream(query)
}
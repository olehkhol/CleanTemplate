package ua.`in`.khol.oleh.cleantemplate.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo
import ua.`in`.khol.oleh.cleantemplate.domain.repository.contract.GitRepository
import javax.inject.Inject

class GetReposUseCase @Inject constructor(private val repository: GitRepository) {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> =
        repository.getSearchResultStream(query)
}
package ua.`in`.khol.oleh.githobbit.data.repository.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.paging.GithubPagingSource
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository

class GitRepositoryImpl(private val service: GitService) : GitRepository {

    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> =
        Pager(
            config = PagingConfig(
                pageSize = service.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubPagingSource(service, query)
            }
        ).flow
}
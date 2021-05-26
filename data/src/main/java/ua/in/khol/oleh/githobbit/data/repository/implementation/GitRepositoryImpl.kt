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
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubPagingSource(service, query)
            }
        ).flow

    companion object {
        // TODO Move this constant out into some 'public file'
        //  currently this constant in 'companion' coz it is used in 'GithubPagingSource.kt'
        const val NETWORK_PAGE_SIZE = 30
    }
}
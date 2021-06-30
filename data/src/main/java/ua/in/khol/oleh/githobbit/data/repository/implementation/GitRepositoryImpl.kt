package ua.`in`.khol.oleh.githobbit.data.repository.implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.paging.DatabasePagingSource
import ua.`in`.khol.oleh.githobbit.data.paging.GitRemoteMediator
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository

class GitRepositoryImpl(
    private val service: GitService,
    private val database: GitDatabase
) : GitRepository {

    @ExperimentalPagingApi
    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        val pagingSourceFactory: () -> DatabasePagingSource = {
            DatabasePagingSource(
                query,
                database
            )
        }

        return Pager(
            config = PagingConfig(
                pageSize = GitService.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GitRemoteMediator(
                query,
                database,
                service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
package ua.`in`.khol.oleh.githobbit.data.repository.implementation

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.paging.GitRemoteMediator
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository

class GitRepositoryImpl(
    private val service: GitService,
    private val database: GitDatabase
) : GitRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {

        // It is very important that these two queries are "in sync",
        // so that both of them end up with the exact same ordering of the Repo's,
        // otherwise we may request a new page, but the API may return data
        // that doesn't result in more data for the corresponding DB query.

        // appending 'in:name' qualifier to synchronize api requests with database requests
        val apiQuery = query + "in:name"
        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"

        return Pager(
            config = PagingConfig(
                pageSize = GitService.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GitRemoteMediator(
                apiQuery,
                service,
                database
            ),
            pagingSourceFactory = {
                database.repoDao().reposByName(dbQuery)
            }
        ).flow.map { pagingData ->
            // Transforms a PagingData<RepoEntity> to the PagingData<Repo>
            pagingData.map { repoEntity ->
                // Transforms a RepoEntity to the Repo
                GitMapper.asRepo(repoEntity)
            }
        }
    }
}
package ua.`in`.khol.oleh.githobbit.data.repository.implementation

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.`in`.khol.oleh.githobbit.data.database.GitDatabase
import ua.`in`.khol.oleh.githobbit.data.database.entity.RepoEntity
import ua.`in`.khol.oleh.githobbit.data.mapper.GitMapper
import ua.`in`.khol.oleh.githobbit.data.network.github.GitService
import ua.`in`.khol.oleh.githobbit.data.paging.GitRemoteMediator
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository

class GitRepositoryImpl(
    private val service: GitService,
    private val database: GitDatabase
) : GitRepository {

    @ExperimentalPagingApi
    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {

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
            pagingSourceFactory = { database.repoDao().reposByName(query) }
        ).flow.map { pagingData: PagingData<RepoEntity> ->
            pagingData.map {
                GitMapper.asRepo(it)
            }
        }
    }
}
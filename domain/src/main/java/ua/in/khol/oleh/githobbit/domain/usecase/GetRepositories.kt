package ua.`in`.khol.oleh.githobbit.domain.usecase

import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Inject

class GetRepositories @Inject constructor(private val gitRepository: GitRepository) {

    suspend fun searchRepository(
        name: String,
        page: Int,
        pageSize: Int
    ): List<Repository> = gitRepository.search(
        name,
        page,
        pageSize
    )
}
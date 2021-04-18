package ua.`in`.khol.oleh.githobbit.domain.usecases

import ua.`in`.khol.oleh.githobbit.domain.models.Repository
import ua.`in`.khol.oleh.githobbit.domain.repositories.GitRepository
import javax.inject.Inject

class SearchRepositoryUseCase @Inject constructor(private val gitRepository: GitRepository) {
    suspend fun searchRepository(
        name: String,
        page: Int,
        pageSize: Int
    ): List<Repository> = gitRepository.search(name, page, pageSize)
}
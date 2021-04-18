package ua.`in`.khol.oleh.githobbit.domain.usecases

import ua.`in`.khol.oleh.githobbit.domain.models.Subscriber
import ua.`in`.khol.oleh.githobbit.domain.repositories.GitRepository
import javax.inject.Inject

class GetSubscribersUseCase @Inject constructor(private val gitRepository: GitRepository) {
    suspend fun getSubscribers(
        owner: String,
        repo: String,
        page: Int,
        pageSize: Int
    ): List<Subscriber> = gitRepository.get(owner, repo, page, pageSize)
}
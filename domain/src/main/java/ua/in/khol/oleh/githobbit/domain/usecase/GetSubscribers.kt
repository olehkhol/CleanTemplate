package ua.`in`.khol.oleh.githobbit.domain.usecase

import ua.`in`.khol.oleh.githobbit.domain.entity.Subscriber
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Inject

class GetSubscribers @Inject constructor(private val gitRepository: GitRepository) {

    suspend fun getSubscribers(
        owner: String,
        repo: String,
        page: Int,
        pageSize: Int
    ): List<Subscriber> = gitRepository.get(
        owner,
        repo,
        page,
        pageSize
    )
}
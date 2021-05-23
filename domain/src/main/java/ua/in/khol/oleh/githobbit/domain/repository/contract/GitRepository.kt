package ua.`in`.khol.oleh.githobbit.domain.repository.contract

import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.domain.entity.Subscriber

interface GitRepository {
    suspend fun search(name: String, page: Int, pageSize: Int): List<Repository>
    suspend fun get(owner: String, repo: String, page: Int, pageSize: Int): List<Subscriber>
}
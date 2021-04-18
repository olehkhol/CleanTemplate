package ua.`in`.khol.oleh.githobbit.domain.repositories

import ua.`in`.khol.oleh.githobbit.domain.models.Repository
import ua.`in`.khol.oleh.githobbit.domain.models.Subscriber

interface GitRepository {
    suspend fun search(name: String, page: Int, pageSize: Int): List<Repository>
    suspend fun get(owner: String, repo: String, page: Int, pageSize: Int): List<Subscriber>
}
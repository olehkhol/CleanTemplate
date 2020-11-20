package ua.`in`.khol.oleh.githobbit.model

import ua.`in`.khol.oleh.githobbit.github.GitHelper
import javax.inject.Inject

class GodRepository @Inject constructor(private val gitHelper: GitHelper) {
    suspend fun searchRepositories(name: String) = gitHelper.searchRepositories(name)
    suspend fun searchMoreRepositories(name: String) = gitHelper.searchMoreRepositories(name)
}
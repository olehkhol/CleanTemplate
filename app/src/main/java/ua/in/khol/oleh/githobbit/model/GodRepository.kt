package ua.`in`.khol.oleh.githobbit.model

import ua.`in`.khol.oleh.githobbit.github.GitHelper
import javax.inject.Inject

class GodRepository @Inject constructor(private val gitHelper: GitHelper) {
    fun searchRepo(name: String) = gitHelper.searchRepo(name)
    fun searchMore(name: String) = gitHelper.searchMore(name)
}
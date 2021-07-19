package ua.`in`.khol.oleh.githobbit.data.network.github

import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.OwnerItem
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepoItem

class RepoFactory {

    fun createRepoItem(id: Long) = RepoItem(
        id = id,
        name = "Name $id",
        ownerItem = OwnerItem(login = "Login $id", avatar_url = ""),
        description = "Description $id",
        stargazers_count = 0,
        forks_count = 0
    )
}
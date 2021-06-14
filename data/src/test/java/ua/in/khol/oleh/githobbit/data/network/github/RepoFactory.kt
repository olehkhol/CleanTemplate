package ua.`in`.khol.oleh.githobbit.data.network.github

import ua.`in`.khol.oleh.githobbit.domain.entity.Repo

class RepoFactory {
    fun create(id: Int): Repo = Repo(
        id = id,
        ownerName = "Owner $id",
        ownerImage = "",
        repoName = "Name $id",
        repoDescription = "Description $id",
        starsCount = 0,
        forksCount = 0
    )
}
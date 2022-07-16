package ua.`in`.khol.oleh.cleantemplate.data.mapper

import ua.`in`.khol.oleh.cleantemplate.data.database.entity.RepoEntity
import ua.`in`.khol.oleh.cleantemplate.data.network.github.serialized.RepoItem
import ua.`in`.khol.oleh.cleantemplate.data.network.github.serialized.SubItem
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo
import ua.`in`.khol.oleh.cleantemplate.domain.model.Sub

object GitMapper {
    fun asRepo(repoItem: RepoItem) = Repo(
        id = repoItem.id,
        ownerName = repoItem.ownerItem.login,
        ownerImage = repoItem.ownerItem.avatar_url,
        repoName = repoItem.name,
        repoDescription = repoItem.description,
        starsCount = repoItem.stargazers_count,
        forksCount = repoItem.forks_count
    )

    fun asRepo(repoEntity: RepoEntity) = Repo(
        id = repoEntity.id,
        ownerName = repoEntity.ownerName,
        ownerImage = repoEntity.ownerImage,
        repoName = repoEntity.name,
        repoDescription = repoEntity.description,
        starsCount = repoEntity.stars,
        forksCount = repoEntity.forks
    )

    fun asRepoEntity(repoItem: RepoItem) = RepoEntity(
        id = repoItem.id,
        name = repoItem.name,
        ownerName = repoItem.ownerItem.login,
        ownerImage = repoItem.ownerItem.avatar_url,
        description = repoItem.description,
        stars = repoItem.stargazers_count,
        forks = repoItem.forks_count
    )

    fun asSub(item: SubItem) = Sub(
        id = item.id,
        name = item.login,
        image = item.avatar_url
    )
}
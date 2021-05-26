package ua.`in`.khol.oleh.githobbit.data.mapper

import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepositoryItem
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SubscriberItem
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.domain.entity.Sub

object GitMapper {
    fun asRepo(item: RepositoryItem) = Repo(
        id = item.id,
        ownerName = item.owner.login,
        ownerImage = item.owner.avatar_url,
        repoName = item.name,
        repoDescription = item.description,
        starsCount = item.stargazers_count,
        forksCount = item.forks_count
    )

    fun asSub(item: SubscriberItem) = Sub(
        id = item.id,
        name = item.login,
        image = item.avatar_url
    )
}
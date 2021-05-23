package ua.`in`.khol.oleh.githobbit.data.mapper

import ua.`in`.khol.oleh.githobbit.data.net.github.serialized.RepositoryItem
import ua.`in`.khol.oleh.githobbit.data.net.github.serialized.SubscriberItem
import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.domain.entity.Subscriber
import javax.inject.Inject

class GitMapper @Inject constructor() {
    fun asRepository(item: RepositoryItem) = Repository(
        id = item.id,
        ownerName = item.owner.login,
        ownerImage = item.owner.avatar_url,
        repoName = item.name,
        repoDescription = item.description,
        starsCount = item.stargazers_count,
        forksCount = item.forks_count
    )

    fun asSubscriber(item: SubscriberItem) = Subscriber(
        id = item.id,
        name = item.login,
        image = item.avatar_url
    )
}
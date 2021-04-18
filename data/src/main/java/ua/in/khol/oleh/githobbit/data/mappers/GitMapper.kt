package ua.`in`.khol.oleh.githobbit.data.mappers

import ua.`in`.khol.oleh.githobbit.data.github.dacl.RepositoryItem
import ua.`in`.khol.oleh.githobbit.data.github.dacl.SubscriberItem
import ua.`in`.khol.oleh.githobbit.domain.models.Repository
import ua.`in`.khol.oleh.githobbit.domain.models.Subscriber
import javax.inject.Inject

class GitMapper @Inject constructor() {
    fun asRepository(it: RepositoryItem) = Repository(
        id = it.id,
        ownerName = it.owner.login,
        ownerImage = it.owner.avatar_url,
        repoName = it.name,
        repoDescription = it.description,
        starsCount = it.stargazers_count,
        forksCount = it.forks_count
    )

    fun asSubscriber(it: SubscriberItem) = Subscriber(
        id = it.id,
        name = it.login,
        image = it.avatar_url
    )
}
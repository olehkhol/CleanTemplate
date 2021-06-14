package ua.`in`.khol.oleh.githobbit.data.network.github

import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepositoryItem
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepositoryOwner
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SearchRepositoryResponse
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SubscriberItem

class FakeGitService(count: Int) : GitService {

    private val items = arrayListOf<RepositoryItem>()

    init {
        for (i in 0 until count)
            items.add(
                RepositoryItem(
                    i,
                    "Name $i",
                    RepositoryOwner("Owner $i", ""),
                    0,
                    "Description $i",
                    0
                )
            )
    }

    override suspend fun searchRepos(
        q: String,
        page: Int,
        perPage: Int
    ): SearchRepositoryResponse {
        val subItems = arrayListOf<RepositoryItem>()
        val fromIndex = (page - 1) * perPage
        var toIndex = page * perPage
        if (toIndex > items.size)
            toIndex = items.size
        if (fromIndex <= toIndex)
            subItems.addAll(items.subList(fromIndex, toIndex))

        return SearchRepositoryResponse(false, subItems, subItems.size)
    }

    override suspend fun getSubs(
        owner: String,
        repo: String,
        page: Int,
        perPage: Int
    ): ArrayList<SubscriberItem> {
        TODO("Not yet implemented")
    }
}
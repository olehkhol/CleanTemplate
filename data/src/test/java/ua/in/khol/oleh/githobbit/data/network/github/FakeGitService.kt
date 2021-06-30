package ua.`in`.khol.oleh.githobbit.data.network.github

import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.OwnerItem
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepoItem
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SearchRepoResponse
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SubItem

class FakeGitService(count: Int) : GitService {

    private val items = arrayListOf<RepoItem>()

    init {
        for (i in 0 until count)
            items.add(
                RepoItem(
                    i.toLong(),
                    "Name $i",
                    OwnerItem("Owner $i", ""),
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
    ): SearchRepoResponse {
        val subItems = arrayListOf<RepoItem>()
        val fromIndex = (page - 1) * perPage
        var toIndex = page * perPage
        if (toIndex > items.size)
            toIndex = items.size
        if (fromIndex <= toIndex)
            subItems.addAll(items.subList(fromIndex, toIndex))

        return SearchRepoResponse(false, subItems, subItems.size)
    }

    override suspend fun getSubs(
        owner: String,
        repo: String,
        page: Int,
        perPage: Int
    ): ArrayList<SubItem> {
        TODO("Not yet implemented")
    }
}
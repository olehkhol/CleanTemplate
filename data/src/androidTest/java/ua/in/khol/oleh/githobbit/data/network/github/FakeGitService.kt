package ua.`in`.khol.oleh.githobbit.data.network.github

import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepoItem
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SearchRepoResponse
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SubItem
import java.io.IOException

class FakeGitService : GitService {

    private val items = arrayListOf<RepoItem>()
    var failureMsg: String? = null

    override suspend fun searchRepos(
        q: String,
        page: Int,
        perPage: Int
    ): SearchRepoResponse {
        failureMsg?.let {
            throw IOException(it)
        }

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

    fun addRepoItem(repo: RepoItem): Boolean = items.add(repo)

    fun clearRepoItems() {
        items.clear()
    }
}
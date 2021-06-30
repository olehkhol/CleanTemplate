package ua.`in`.khol.oleh.githobbit.data.network.github.serialized

import com.google.gson.annotations.SerializedName

data class SearchRepoResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val repoItems: List<RepoItem>,
    @SerializedName("total_count")
    val total_count: Int
)

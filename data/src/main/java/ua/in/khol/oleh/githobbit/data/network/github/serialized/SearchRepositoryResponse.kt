package ua.`in`.khol.oleh.githobbit.data.network.github.serialized

import com.google.gson.annotations.SerializedName
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.RepositoryItem

data class SearchRepositoryResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val items: List<RepositoryItem>,
    @SerializedName("total_count")
    val total_count: Int
)

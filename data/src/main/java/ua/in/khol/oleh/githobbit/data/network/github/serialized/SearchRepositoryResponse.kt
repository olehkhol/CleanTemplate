package ua.`in`.khol.oleh.githobbit.data.net.github.serialized

import com.google.gson.annotations.SerializedName

data class SearchRepositoryResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val items: List<RepositoryItem>,
    val total_count: Int
)

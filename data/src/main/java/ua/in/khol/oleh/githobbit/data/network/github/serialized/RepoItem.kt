package ua.`in`.khol.oleh.githobbit.data.network.github.serialized

import com.google.gson.annotations.SerializedName

data class RepoItem(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val ownerItem: OwnerItem,
    @SerializedName("stargazers_count")
    val stargazers_count: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("forks_count")
    val forks_count: Int,
)

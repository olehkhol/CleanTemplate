package ua.`in`.khol.oleh.githobbit.data.network.github.serialized

import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: RepositoryOwner,
    @SerializedName("stargazers_count")
    val stargazers_count: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("forks_count")
    val forks_count: Int,
)

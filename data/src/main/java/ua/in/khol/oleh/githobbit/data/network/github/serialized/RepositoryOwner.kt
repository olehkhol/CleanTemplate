package ua.`in`.khol.oleh.githobbit.data.network.github.serialized

import com.google.gson.annotations.SerializedName

data class RepositoryOwner(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatar_url: String,
)
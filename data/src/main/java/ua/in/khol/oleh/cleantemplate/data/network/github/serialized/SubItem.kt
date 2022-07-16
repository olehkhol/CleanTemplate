package ua.`in`.khol.oleh.cleantemplate.data.network.github.serialized

import com.google.gson.annotations.SerializedName

data class SubItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatar_url: String,
)
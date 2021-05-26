package ua.`in`.khol.oleh.githobbit.data.network.github.serialized

import com.google.gson.annotations.SerializedName

data class SubscriberItem(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Int,
    val node_id: String,
    @SerializedName("avatar_url")
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
    val type: String,
    val site_admin: Boolean
)
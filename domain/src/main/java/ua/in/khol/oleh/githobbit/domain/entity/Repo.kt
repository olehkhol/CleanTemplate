package ua.`in`.khol.oleh.githobbit.domain.entity

import java.io.Serializable

data class Repo(
    val id: Int = 0,
    val ownerName: String = "",
    val ownerImage: String = "",
    val repoName: String = "",
    val repoDescription: String? = "",
    val starsCount: Int = 0,
    val forksCount: Int = 0
) : Serializable // Need it to be 'Serializable' to send 'Repo' instance to other activity
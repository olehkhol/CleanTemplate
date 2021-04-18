package ua.`in`.khol.oleh.githobbit.domain.models

import java.io.Serializable

data class Subscriber(
    val id: Int = 0,
    val name: String = "",
    val image: String = ""
) : Serializable
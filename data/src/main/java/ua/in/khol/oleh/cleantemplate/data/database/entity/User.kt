package ua.`in`.khol.oleh.cleantemplate.data.database.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
    @Id
    var id: Long = 0,
    var name: String? = null
)

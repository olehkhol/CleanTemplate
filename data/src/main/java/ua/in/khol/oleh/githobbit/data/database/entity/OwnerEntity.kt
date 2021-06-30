package ua.`in`.khol.oleh.githobbit.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
data class OwnerEntity(
    @PrimaryKey
    val id: Long
)

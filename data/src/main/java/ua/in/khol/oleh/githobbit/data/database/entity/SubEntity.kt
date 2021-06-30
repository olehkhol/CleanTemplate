package ua.`in`.khol.oleh.githobbit.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub")
data class SubEntity(
    @PrimaryKey val id: Long
)
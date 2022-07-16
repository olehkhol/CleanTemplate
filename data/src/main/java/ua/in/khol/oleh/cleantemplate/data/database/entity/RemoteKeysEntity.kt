package ua.`in`.khol.oleh.cleantemplate.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
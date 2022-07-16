package ua.`in`.khol.oleh.cleantemplate.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class RepoEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner_name") val ownerName: String,
    @ColumnInfo(name = "owner_image") val ownerImage: String,
    @ColumnInfo(name = "stars") val stars: Int,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "forks") val forks: Int,
)

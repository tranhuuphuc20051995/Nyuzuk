package com.andres.nyuzuk.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val publisher: BasePublisherEntity?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @PrimaryKey val url: String
)
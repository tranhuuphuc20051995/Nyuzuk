package com.andres.nyuzuk.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andres.nyuzuk.data.entity.local.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(articlesEntity: List<ArticleEntity>)

    @Query("SELECT * FROM article")
    fun get(): List<ArticleEntity>

    @Query("DELETE FROM article WHERE isTop = 1")
    fun invalidateTopArticles()
}
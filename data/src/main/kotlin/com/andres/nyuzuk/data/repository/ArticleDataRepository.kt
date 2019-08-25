package com.andres.nyuzuk.data.repository

import arrow.core.Either
import arrow.core.flatMap
import com.andres.nyuzuk.data.datasource.local.ArticleLocalDataSource
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.repository.ArticleRepository

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleMapper: ArticleMapper
) : ArticleRepository {
    override suspend fun getTopArticles(invalidating: Boolean) =
        articleRemoteDataSource.getTopArticles(invalidating).flatMap { Either.Right(articleMapper.mapFromRemote(it)) }

    override suspend fun searchArticles(query: String, invalidating: Boolean) =
        articleRemoteDataSource.searchArticles(query, invalidating).flatMap { Either.Right(articleMapper.mapFromRemote(it)) }
}
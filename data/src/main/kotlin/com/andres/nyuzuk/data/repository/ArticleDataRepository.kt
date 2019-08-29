package com.andres.nyuzuk.data.repository

import arrow.core.Either
import arrow.core.flatMap
import com.andres.nyuzuk.data.datasource.local.ArticleLocalDataSource
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleMapper: ArticleMapper
) : ArticleRepository {
    override suspend fun getTopArticles(invalidate: Boolean, fetchMore: Boolean): Flow<Either<Failure, List<Article>>> {
        return flow {
            if (!invalidate && !fetchMore) {
                val topArticlesLocal = articleMapper.mapFromLocal(articleLocalDataSource.get())
                emit(Either.Right(topArticlesLocal))
            } else if (invalidate) {
                articleLocalDataSource.invalidateTopArticles()
            }
            val topArticlesRemote = articleRemoteDataSource.getTopArticles(invalidate)
            if (topArticlesRemote.isLeft()) {
                emit(Either.Left(Failure.NetworkConnection))
            } else {
                val topArticles = topArticlesRemote.map {
                    val topArticles = articleMapper.mapFromRemote(it)
                    articleLocalDataSource.save(articleMapper.mapToLocal(topArticles))
                    topArticles
                }
                emit(topArticles)
            }
        }
    }

    override suspend fun searchArticles(query: String, invalidate: Boolean) = flow {
        emit(
            articleRemoteDataSource.searchArticles(
                query,
                invalidate
            ).flatMap { Either.Right(articleMapper.mapFromRemote(it)) })
    }
}
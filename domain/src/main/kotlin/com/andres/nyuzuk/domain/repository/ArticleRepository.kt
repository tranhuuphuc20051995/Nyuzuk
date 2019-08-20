package com.andres.nyuzuk.domain.repository

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article

interface ArticleRepository {
    suspend fun getTopArticles(): Either<Failure, List<Article>>
    suspend fun searchArticles(query: String): Either<Failure, List<Article>>
}
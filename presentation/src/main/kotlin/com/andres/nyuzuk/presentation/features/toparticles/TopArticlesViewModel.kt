package com.andres.nyuzuk.presentation.features.toparticles

import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.GetTopArticles
import com.andres.nyuzuk.presentation.base.BaseViewModel

class TopArticlesViewModel(
    private val getTopArticles: GetTopArticles,
    private val articleUiMapper: ArticleUiMapper
) : BaseViewModel<TopArticlesViewState>(), ArticleClickListener {
    override fun initViewState() {
        viewState.value = TopArticlesViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(isLoading = true)
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onLoadMore() {
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onRefresh() {
        viewState.value = getViewState().copy(isLoading = true)
        getTopArticles(viewModelScope, GetTopArticles.Params(true)) { it.fold(::processFailure, ::processSuccess) }
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        // TODO navigate to article detail
    }

    private fun processSuccess(articles: List<Article>) {
        viewState.value = getViewState().copy(isLoading = false, isError = false)
        val articlesUi = articleUiMapper.map(articles)
        if (articlesUi.isEmpty()) {
            viewState.value = getViewState().copy(isEmpty = true)
        } else {
            viewState.value =
                getViewState().copy(topArticlesUi = articlesUi, isEmpty = false)
        }
    }

    private fun processFailure(failure: Failure) {
        viewState.value = getViewState().copy(isError = true, isLoading = false)
    }
}
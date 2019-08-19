package com.andres.nyuzuk.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.usecase.GetTopHeadlines

class MainViewModel(
    private val getTopHeadlines: GetTopHeadlines
): ViewModel() {
    fun onInit() {
        getTopHeadlines(viewModelScope) {
            it.either({
                // TODO render error
            }, { articles ->
                println(articles.toString())
            })
        }
    }
}
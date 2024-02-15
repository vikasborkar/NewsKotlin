package com.vikas.android.newskotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikas.android.newskotlin.data.Article
import com.vikas.android.newskotlin.data.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel : ViewModel() {
    private val articleRepository = ArticleRepository.get()

    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
    val articles: StateFlow<List<Article>>
        get() = _articles.asStateFlow()

    init {
        viewModelScope.launch { setQuery() }
    }

    fun setQuery(query: String = "") {
        viewModelScope.launch { fetchArticles(query) }
    }

    private suspend fun fetchArticles(query: String) {
        _articles.value = if (query.isEmpty()) {
            val newArticles = articleRepository.getArticles()
            newArticles
        } else {
            articleRepository.queryArticles(query)
        }
    }
}
package com.vikas.android.newskotlin.data

import android.content.Context

class ArticleRepository private constructor(applicationContext: Context) {

    private val dataStore = DataStore.getInstance(applicationContext)
    private lateinit var articles: List<Article>

    suspend fun getArticles(): List<Article> {
        return dataStore.getArticles().sortedBy { it.publishedAt }.reversed()
    }

    suspend fun queryArticles(query: String): List<Article> {
        return dataStore.queryArticles(query)
    }


    companion object {
        private var INSTANCE: ArticleRepository? = null

        fun init(applicationContext: Context) {
            if (INSTANCE == null) {
                INSTANCE = ArticleRepository(applicationContext)
            }
        }

        fun get(): ArticleRepository {
            return INSTANCE ?: throw IllegalStateException("ArticleRepository must be initialized")
        }
    }
}
package com.vikas.android.newskotlin.util

import com.vikas.android.newskotlin.data.Article

fun List<Article>.sortByDate(): List<Article> {
    return this.sortedBy { it.publishedAt }.reversed()
}
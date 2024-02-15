package com.vikas.android.newskotlin.data

import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Article constructor(val title: String, val publishedAt: Date, val urlToImage: String, val url: String) {

    companion object {
        fun fromJson(jsonObject: JSONObject): Article {

            val title = if (jsonObject.has("title")) jsonObject.getString("title") else null
            require(!title.isNullOrEmpty()) { "Article must have a title" }

            val url = if (jsonObject.has("url")) jsonObject.getString("url") else ""
            val urlToImage = if (jsonObject.has("urlToImage")) jsonObject.getString("urlToImage") else ""
            val publishedAtString = if (jsonObject.has("publishedAt")) jsonObject.getString("publishedAt") else ""
            val publishedAt = try {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(publishedAtString)
            } catch (e: ParseException) {
                Date(Long.MIN_VALUE)
            }

            return Article(title, publishedAt, urlToImage, url)
        }
    }
}

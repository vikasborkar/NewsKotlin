package com.vikas.android.newskotlin.data

import android.content.Context
import android.util.Log
import com.vikas.android.newskotlin.util.sortByDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream

/*
*
* This class mimics the data source like db, retrofit
* */

private const val JSON_FILE_NAME = "news.json"
private const val TAG = "DataStore"

class DataStore private constructor(val context: Context) {

    /*
    * mimics getting data from db/api
    * */
    suspend fun getArticles(): List<Article> = withContext(Dispatchers.IO) {
        parseJsonData(getJsonString()).sortByDate()
    }

    /*
    * mimics making search query to db/api
    * made it on purpose for the task/test/to-follow-std-code-pattern, the article list returned by parseJsonData() can be stored in a variable
    * */
    suspend fun queryArticles(query: String): List<Article> = withContext(Dispatchers.Default) {
        val articles = getArticles() // or stored article list
        //simple, query contains based search
        val queriedArticles = articles.filter {
            it.title.contains(query, true)
        }
        queriedArticles
    }


    private fun getJsonString(): String {
        val inputStream: InputStream = context.assets.open(JSON_FILE_NAME)
        return inputStream.bufferedReader().use { it.readText() }
    }

    suspend fun parseJsonData(jsonString: String): List<Article> = withContext(Dispatchers.IO) {

        System.out.println("Test")
        System.out.println("Received Json: " + jsonString)

        val articles = mutableListOf<Article>()

        try {
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("articles")
            System.out.println("jsonArray.length(): " + jsonArray.length())
            for (i in 0 until jsonArray.length()) {
                val article = Article.fromJson(jsonArray.getJSONObject(i))
                articles.add(article)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Error in parsing json", e)
        }

        articles
    }

    companion object {
        private var INSTANCE: DataStore? = null

        fun getInstance(applicationContext: Context): DataStore {
            if (INSTANCE == null) {
                INSTANCE = DataStore(applicationContext)
            }
            return INSTANCE!!
        }
    }
}


package com.vikas.android.newskotlin

import android.content.Context
import com.vikas.android.newskotlin.data.Article
import com.vikas.android.newskotlin.data.DataStore
import com.vikas.android.newskotlin.util.sortByDate
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RunWith(MockitoJUnitRunner::class)

class NewsUnitTest {
    val context = mock(Context::class.java)

    val jsonData = """
        {
            "articles":
                [
                    {"title": "News 2", "publishedAt": "2024-01-09T21:41:56Z", "urlToImage": "urlToImage2", "url":"url2"},
                    {"title": "News 1", "publishedAt": "2024-01-10T22:41:25Z", "urlToImage": "urlToImage1", "url":"url1"},
                    {"title": "News 3", "publishedAt": "2024-01-03T16:41:52Z", "urlToImage": "urlToImage3", "url":"url3"}
                ]
        }
    """.trimIndent()

    // Sample function to load news from JSON data (not implemented)
    suspend fun loadNewsFromJson(jsonData: String): List<Article> {
        return DataStore.getInstance(context).parseJsonData(jsonData)
    }

    // Helper function to parse date string into Date object
    fun parseDate(dateString: String): Date {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(dateString) ?: Date()
        } catch (e: ParseException) {
            Date()
        }
    }

    @Test
    fun testLoadNewsFromJson() = runTest {
        val expected = listOf(
            Article("News 2", parseDate("2024-01-09T21:41:56Z"), "urlToImage2", "url2"),
            Article("News 1", parseDate("2024-01-10T22:41:25Z"), "urlToImage1", "url1"),
            Article("News 3", parseDate("2024-01-03T16:41:52Z"), "urlToImage3", "url3")
        )
        val actual = loadNewsFromJson(jsonData)
        assertEquals(expected, actual)
    }

    @Test
    fun testSortNewsByPublishedDate() {
        val expected = listOf(
            Article("News 1", parseDate("2024-01-10T22:41:25Z"), "urlToImage1", "url1"),
            Article("News 2", parseDate("2024-01-09T21:41:56Z"), "urlToImage2", "url2"),
            Article("News 3", parseDate("2024-01-03T16:41:52Z"), "urlToImage3", "url3")
        )

        val random = listOf(
            Article("News 2", parseDate("2024-01-09T21:41:56Z"), "urlToImage2", "url2"),
            Article("News 1", parseDate("2024-01-10T22:41:25Z"), "urlToImage1", "url1"),
            Article("News 3", parseDate("2024-01-03T16:41:52Z"), "urlToImage3", "url3")
        )

        val sorted = random.sortByDate()

        assertEquals(expected, sorted)
    }
}
package com.vikas.android.newskotlin

import android.app.Application
import com.vikas.android.newskotlin.data.ArticleRepository

class NewsApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ArticleRepository.init(this)
    }
}
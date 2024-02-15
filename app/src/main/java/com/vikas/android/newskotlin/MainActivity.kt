package com.vikas.android.newskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.MenuHost

class MainActivity : AppCompatActivity(), MenuHost {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
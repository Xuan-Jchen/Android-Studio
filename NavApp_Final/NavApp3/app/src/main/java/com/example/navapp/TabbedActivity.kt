package com.example.navapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navapp.R

class TabbedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置当前 Activity 的布局文件
        setContentView(R.layout.activity_tabbed)
    }
}

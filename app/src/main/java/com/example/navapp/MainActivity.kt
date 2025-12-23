package com.example.navapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.actionGotoDrawerActivity.setOnClickListener {
            startActivity(Intent(this, DrawerActivity::class.java))
        }

        binding.actionGotoBottomActivity.setOnClickListener {
            startActivity(Intent(this, BottomActivity::class.java))
        }

        binding.actionGotoOptionsActivity.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java))
        }

        binding.actionGotoTabbedActivity.setOnClickListener {
            startActivity(Intent(this, TabbedActivity::class.java))
        }
    }
}

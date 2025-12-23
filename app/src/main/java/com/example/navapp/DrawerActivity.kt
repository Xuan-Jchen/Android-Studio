package com.example.navapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.navapp.databinding.ActivityDrawerBinding

class DrawerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawerBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // 顶级目的地（不会显示返回箭头）
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.drawer1Fragment,
                R.id.drawer2Fragment
            ),
            binding.drawerLayout
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    // 让 Toolbar 左上角按钮（≡）正常工作
    override fun onSupportNavigateUp(): Boolean {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                .navController
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}

package com.example.navapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.navapp.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {

    // ViewBinding 对象，用于访问 layout 中的控件
    private lateinit var binding: ActivityOptionsBinding

    // NavController 用于控制导航（Fragment 跳转）
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 通过 ViewBinding inflate 布局
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        // 设置根布局为 activity 的内容视图
        setContentView(binding.root)

        // 设置 Toolbar 为支持 ActionBar
        setSupportActionBar(binding.toolbar)

        // 配置 AppBar，指定 top-level fragment（没有返回箭头的 Fragment）
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.options1Fragment2, R.id.options2Fragment2
        ).build()

        // 获取 NavHostFragment，它承载 Fragment 容器
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // 获取 NavController，用于导航操作
        navController = navHostFragment.navController

        // 将 Toolbar 与 NavController 绑定，自动处理标题和返回按钮
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    // 创建 Toolbar 菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 加载 menu/options_menu.xml
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    // Toolbar 菜单项点击处理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 使用 NavigationUI 处理菜单点击跳转到对应 Fragment
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }
}

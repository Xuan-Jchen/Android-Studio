package com.example.navapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.navapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        observeNavigationState()
    }

    private fun setupNavigation() {
        // 设置 Toolbar
        setSupportActionBar(binding.toolbar)

        // 获取视图引用
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // 获取 NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 配置顶层目的地（不显示返回按钮的页面）
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.tabbed1Fragment,      // Tab 页面
                R.id.drawer_graph,       // Drawer 第一个页面
                R.id.bottom_graph        // Bottom 第一个页面
            ),
            drawerLayout
        )

        // 自动设置 Toolbar 与 Navigation 的联动
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 自动设置 NavigationView（Drawer）与 NavController 的联动
        // 这会自动处理 Drawer 菜单项的点击事件
        navigationView.setupWithNavController(navController)

        // 自动设置 BottomNavigationView 与 NavController 的联动
        // 这会自动处理 Bottom 菜单项的点击事件
        bottomNavView.setupWithNavController(navController)
    }

    private fun observeNavigationState() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // 使用现代的协程 Flow 方式监听导航变化
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navController.currentBackStackEntryFlow.collect { backStackEntry ->
                    updateUIVisibility(
                        destinationId = backStackEntry.destination.id,
                        drawerLayout = drawerLayout,
                        bottomNavView = bottomNavView
                    )
                }
            }
        }
    }

    private fun updateUIVisibility(
        destinationId: Int,
        drawerLayout: DrawerLayout,
        bottomNavView: BottomNavigationView
    ) {
        when (destinationId) {
            // Tab 页面：隐藏 Bottom Navigation，锁定 Drawer
            R.id.tabbed1Fragment -> {
                bottomNavView.isVisible = true
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

            // Drawer 页面：显示 Bottom Navigation，启用 Drawer
            R.id.drawer1Fragment,
            R.id.drawer2Fragment,
            R.id.drawer3Fragment -> {
                bottomNavView.isVisible = true
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

            // Bottom Navigation 页面：显示 Bottom Navigation，启用 Drawer
            R.id.bottom1Fragment,
            R.id.bottom2Fragment,
            R.id.bottom3Fragment -> {
                bottomNavView.isVisible = true
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

            // Options 页面：隐藏 Bottom Navigation，启用 Drawer
            R.id.options1Fragment2,
            R.id.options2Fragment2,
            R.id.options3Fragment2 -> {
                bottomNavView.isVisible = false
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

            // 默认状态
            else -> {
                bottomNavView.isVisible = true
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 加载 Options Menu
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 使用 NavigationUI 自动处理 Options Menu 的导航
        // 这会自动根据 menu item 的 ID 导航到对应的 Fragment
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        // 处理 Toolbar 的返回按钮和 Drawer 的打开
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
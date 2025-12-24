package com.example.navapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.navapp.databinding.FragmentTabbed1Binding
import com.google.android.material.tabs.TabLayoutMediator

class Tabbed1Fragment : Fragment() {

    // ViewBinding 变量，安全访问布局控件
    private var _binding: FragmentTabbed1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate 布局并返回根视图
        _binding = FragmentTabbed1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置 ViewPager2 适配器
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3 // 总共 3 个页面

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> Tabbed1AFragment() // 第一个页面
                    1 -> Tabbed1BFragment() // 第二个页面
                    2 -> Tabbed1CFragment() // 第三个页面
                    else -> Tabbed1AFragment() // 默认返回第一个
                }
            }
        }

        // 将 TabLayout 与 ViewPager2 绑定，实现标题显示和滑动联动
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // 配置每个 Tab 的标题
            tab.text = when (position) {
                0 -> "TAB A"
                1 -> "TAB B"
                2 -> "TAB C"
                else -> "TAB A"
            }
        }.attach() // 必须调用 attach() 才生效
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 避免内存泄漏
    }
}

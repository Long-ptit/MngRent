package com.restaurant.exam.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import restaurant.exam.R
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.base.PagerFragmentAdapter
import restaurant.exam.databinding.LayoutMainBinding
import com.restaurant.exam.ui.home.HomeFragment
import com.restaurant.exam.ui.menu.MenuFragment
import com.restaurant.exam.ui.news.NewsFragment
import com.restaurant.exam.ui.user.UserFragment
import com.restaurant.exam.ui.statistic.StatisticFragment
import com.restaurant.exam.view_model.ViewModelFactory


class MainActivity : BaseActivity<MainViewModel, LayoutMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener{
    private lateinit var mPagerAdapter : PagerFragmentAdapter

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
    override fun getContentLayout(): Int {
        return R.layout.layout_main
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[MainViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        initFragment()
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
    }

    override fun initListener() {
    }

    override fun observerLiveData() {
    }

    private fun initFragment() {
        mPagerAdapter = PagerFragmentAdapter(supportFragmentManager)
        mPagerAdapter.addFragment(NewsFragment())
        mPagerAdapter.addFragment(StatisticFragment())
        mPagerAdapter.addFragment(HomeFragment())
        mPagerAdapter.addFragment(MenuFragment())
        mPagerAdapter.addFragment(UserFragment())
        binding.viewPager.adapter = mPagerAdapter
        binding.viewPager.offscreenPageLimit = mPagerAdapter.count
        binding.viewPager.currentItem = 2
        binding.bottomNav.selectedItemId = R.id.action_home
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_news -> {
                binding.viewPager.currentItem = 0
                return true
            }
            R.id.action_statistic -> {
                binding.viewPager.currentItem = 1
                return true
            }
            R.id.action_home -> {
                binding.viewPager.currentItem = 2
                return true
            }
            R.id.action_menu -> {
                binding.viewPager.currentItem = 3
                return true
            }
            R.id.action_acc -> {
                binding.viewPager.currentItem = 4
                return true
            }
        }
        return false
    }
}

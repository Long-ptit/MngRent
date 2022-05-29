package com.restaurant.exam.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayoutMediator
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.ui.login.LoginActivity
import com.restaurant.exam.ui.login.LoginViewModel
import com.restaurant.exam.ui.main.MainActivity
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity<LoginViewModel, ActivityIntroBinding>() {
    var runnable: Runnable? = null
    var handler: Handler? = null
    private var currently = 0
    private var check: Boolean = false

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, IntroActivity::class.java)
        }
    }
    override fun getContentLayout(): Int {
        return R.layout.activity_intro
    }

    override fun observerLiveData() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]
    }

    override fun initView() {
        transparentStatusbar2()
        showError = true
    }

    override fun initListener() {
        binding.viewPager.adapter = OnboardAD(this, this)
        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { _, _ -> }.attach()
        binding.viewPager.offscreenPageLimit = 1
        handler = Handler()
        runnable = Runnable {
            currently++
            if (currently >= 3) {
                currently = 0
            }
            binding.viewPager.setCurrentItem(currently, true)
            handler!!.postDelayed(runnable!!, 4500)
        }
        handler!!.postDelayed(runnable!!, 4500)
        binding.btnContinue.setOnClickListener {
            viewModel.setStart(true)
            startActivity(LoginActivity.getIntent(this))
            finish()
        }


    }

    override fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginViewModel::class.java)
    }

}

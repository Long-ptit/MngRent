package com.restaurant.exam.ui.splash

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import restaurant.exam.R
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.firebase.NotificationSend
import com.restaurant.exam.ui.intro.IntroActivity
import com.restaurant.exam.ui.login.LoginActivity
import com.restaurant.exam.ui.login.LoginViewModel
import restaurant.exam.databinding.LayoutSplashBinding
import com.restaurant.exam.ui.main.MainActivity
import com.restaurant.exam.ui.main.MainActivityStaff
import com.restaurant.exam.ui.main.MainViewModel
import com.restaurant.exam.utils.FirebaseUtils
import com.restaurant.exam.view_model.ViewModelFactory


class SplashActivity : BaseActivity<LoginViewModel, LayoutSplashBinding>() {
    lateinit var handler: Handler

    override fun getContentLayout(): Int {
        return R.layout.layout_splash
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]
    }

    override fun initView() {

    }

    override fun initListener() {
        handler = Handler()
        handler.postDelayed({
            if (!viewModel.isStart()) {
                startActivity(IntroActivity.getIntent(this))
                Log.d("ptit", "initListener: ")
                finish()
            } else {
                var intent: Intent
                if (viewModel.checkUserLogin()) {
                    viewModel.initFirebase()
                    if (viewModel.checkIsStaff()) {
                        intent =  Intent(
                            this,
                            MainActivityStaff::class.java
                        )
                    } else {
                        intent =  Intent(
                            this,
                            MainActivity::class.java
                        )
                    }
                } else {
                    intent =  Intent(
                        this,
                        LoginActivity::class.java
                    )
                }
                startActivity(intent)
                finish()
            }
        }, 1000)


    }

    override fun observerLiveData() {

    }
}

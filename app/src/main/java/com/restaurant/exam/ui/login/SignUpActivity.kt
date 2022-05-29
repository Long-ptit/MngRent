package com.restaurant.exam.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.ui.main.MainActivity
import com.restaurant.exam.ui.main.MainActivityStaff
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutSignupBinding

class SignUpActivity : BaseActivity<LoginViewModel, LayoutSignupBinding>() {

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.layout_signup
    }

    override fun observerLiveData() {
        viewModel.apply {
            signUpResponse.observe(this@SignUpActivity, androidx.lifecycle.Observer {
                showError("Đăng kí thành công")
                    startActivity(MainActivity.getIntent(this@SignUpActivity))
                    finish()
            })
        }
    }

    override fun initView() {
        showError = true
    }

    override fun initListener() {
        binding.btnSave.setOnClickListener {
            val staff = Staff(
                name = binding.edtName.text.toString(),
                phone = binding.edtPhone.text.toString(),
                address = binding.edtAddress.text.toString(),
                username = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString(),
                restaurant = Restaurant(
                    name = binding.edtNameRestaurant.text.toString(),
                    address = binding.edtAddress.text.toString(),
                    phone = binding.edtPhone.text.toString()
                ),
                role = "admin"
            )
            viewModel.signup(staff)
        }

    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]
    }

}
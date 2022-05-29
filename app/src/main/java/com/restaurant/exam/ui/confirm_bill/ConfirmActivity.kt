package com.restaurant.exam.ui.confirm_bill

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Bill
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.FoodBill
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.ui.login.LoginViewModel
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutConfirmBillBinding
import restaurant.exam.databinding.LayoutLoginBinding

class ConfirmActivity : BaseActivity<ConfirmViewModel, LayoutConfirmBillBinding>() {

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, ConfirmActivity::class.java)
        }
    }

    lateinit var list: ArrayList<Food>

    override fun getContentLayout(): Int {
        return R.layout.layout_confirm_bill
    }

    override fun observerLiveData() {
//        viewModel.apply {
//            loginResponse.observe(this@LoginActivity, androidx.lifecycle.Observer {
//                if (it != null) {
//                    showError(it.status)
//                    if (it.data != null) {
//                        startActivity(MainActivity.getIntent(this@LoginActivity))
//                        finish()
//                    }
//                }
//            })
//        }
    }

    override fun initView() {
        showError = true
        if (intent.hasExtra("list")) {
            list  = intent.getSerializableExtra("list") as ArrayList<Food>
            Log.d("ptit", "initView size: " + list.size)
        }

    }

    override fun initListener() {
        val foodBill = FoodBill()
        foodBill.data = list
        foodBill.bill = Bill()
        viewModel.save(foodBill)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[ConfirmViewModel::class.java]
    }

}
package com.restaurant.exam.ui.manage_staff

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Floor
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.data.model.TableRestaurant
import com.restaurant.exam.ui.detail_food.AddFoodActivity
import com.restaurant.exam.ui.manage.ManageRestaurantViewModel
import com.restaurant.exam.ui.manage.adapter.SpinnerAdapterFloor
import com.restaurant.exam.ui.manage.adapter.TableAddAdapter
import com.restaurant.exam.ui.manage_staff.adapter.StaffAdapter
import com.restaurant.exam.ui.menu.adapter.MenuFoodAdapter
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutManageRestaurantBinding
import restaurant.exam.databinding.LayoutManageStaffBinding

class ManageStaffActivity :
    BaseActivity<ManageStaffViewModel, LayoutManageStaffBinding>(), StaffAdapter.IClick{

    lateinit var mAdapter: StaffAdapter

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, ManageStaffActivity::class.java)
        }
    }


    override fun getContentLayout(): Int {
        return R.layout.layout_manage_staff
    }

    override fun observerLiveData() {

//
        viewModel.apply {
            getStaffResponse.observe(this@ManageStaffActivity) {
                if (it != null) {
                    mAdapter.setList(it as ArrayList<Staff>)
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStaff()
    }

    override fun initView() {
        showError = true
        mAdapter = StaffAdapter(this)
        binding.rvHome.adapter = mAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun initListener() {
        viewModel.getStaff()
        binding.fab.setOnClickListener {
            var intent = Intent(
                this,
                AddStaffActivity::class.java
            )
            startActivity(intent)
        }

    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ManageStaffViewModel::class.java]
    }


    override fun onClick(data: Staff) {
        var intent = Intent(
            this,
            DetailStaffActivity::class.java
        )
        intent.putExtra("staff", data);
        startActivity(intent)
    }


}
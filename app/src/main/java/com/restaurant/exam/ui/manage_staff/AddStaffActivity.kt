package com.restaurant.exam.ui.manage_staff

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.ui.manage_staff.adapter.StaffAdapter
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutAddStaffBinding
import restaurant.exam.databinding.LayoutManageStaffBinding

class AddStaffActivity :
    BaseActivity<ManageStaffViewModel, LayoutAddStaffBinding>(){

    lateinit var mAdapter: StaffAdapter

    override fun getContentLayout(): Int {
        return R.layout.layout_add_staff
    }

    override fun observerLiveData() {
        viewModel.apply {
            saveStaffResponse.observe(this@AddStaffActivity) {
                if (it != null) {
                    showError("Thêm nhân viên thành công với id là ${it.id}")
                    finish()
                }

            }
        }
    }

    override fun initView() {
        showError = true

    }

    override fun initListener() {
        binding.btnSave.setOnClickListener {
            val staff = Staff(
                name = binding.edtName.text.toString(),
                username =  binding.edtEmail.text.toString(),
                password =  binding.edtPassword.text.toString(),
                restaurant = Restaurant(viewModel.getResId()),
                phone = binding.edtPhone.text.toString(),
                address = binding.edtAddress.text.toString(),
                role = "nhanvien"
            )
            viewModel.saveStaff(staff)
        }
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ManageStaffViewModel::class.java]
    }




}
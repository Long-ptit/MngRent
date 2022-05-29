package com.restaurant.exam.ui.manage_staff

import androidx.lifecycle.ViewModelProvider
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutAddStaffBinding
import restaurant.exam.databinding.LayoutDetailProfileStaffBinding
import restaurant.exam.databinding.LayoutDetailStaffBinding

class DetailProfileStaffActivity :
    BaseActivity<ManageStaffViewModel, LayoutDetailProfileStaffBinding>(){


    override fun getContentLayout(): Int {
        return R.layout.layout_detail_profile_staff
    }

    var mStaff = Staff()

    override fun observerLiveData() {
        viewModel.apply {
            saveStaffResponse.observe(this@DetailProfileStaffActivity) {
                if (it != null) {
                    finish()
                }

            }
        }

        viewModel.apply {
            getStaffIDResponse.observe(this@DetailProfileStaffActivity) {
                if (it != null) {
                    mStaff = it

                    binding.edtName.setText(mStaff.name)
                    binding.edtAddress.setText(mStaff.address)
                    binding.edtEmail.setText(mStaff.username)
                    binding.edtPhone.setText(mStaff.phone)
                    binding.edtPassword.setText(mStaff.password)
                }

            }
        }
    }

    override fun initView() {
        showError = true
        viewModel.getStaffID()
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
                role = "nhanvien",
                id = mStaff.id
            )
            viewModel.saveStaff(staff)
        }
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ManageStaffViewModel::class.java]
    }




}
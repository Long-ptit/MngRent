package com.restaurant.exam.ui.manage_staff

import androidx.lifecycle.ViewModelProvider
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutAddStaffBinding
import restaurant.exam.databinding.LayoutDetailProfileAdminBinding
import restaurant.exam.databinding.LayoutDetailStaffBinding

class DetailProfileActivity :
    BaseActivity<ManageStaffViewModel, LayoutDetailProfileAdminBinding>(){


    override fun getContentLayout(): Int {
        return R.layout.layout_detail_profile_admin
    }

    var mStaff = Staff()

    override fun observerLiveData() {
        viewModel.apply {
            saveStaffAdminResponse.observe(this@DetailProfileActivity) {
                if (it != null) {
                    finish()
                }

            }
        }

        viewModel.apply {
            getStaffIDResponse.observe(this@DetailProfileActivity) {
                if (it != null) {
                    mStaff = it

                    binding.edtName.setText(mStaff.name)
                    binding.edtAddress.setText(mStaff.address)
                    binding.edtEmail.setText(mStaff.username)
                    binding.edtPhone.setText(mStaff.phone)
                    binding.edtPassword.setText(mStaff.password)
                    binding.edtNameRestaurant.setText(mStaff.restaurant?.name)
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
                phone = binding.edtPhone.text.toString(),
                address = binding.edtAddress.text.toString(),
                username = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString(),
                restaurant = Restaurant(
                    name = binding.edtNameRestaurant.text.toString(),
                    address = binding.edtAddress.text.toString(),
                    phone = binding.edtPhone.text.toString(),
                    id = mStaff.restaurant?.id
                ),
                role = "admin",
                id = mStaff.id
            )
            viewModel.saveStaffAdmin(staff)
        }
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ManageStaffViewModel::class.java]
    }




}
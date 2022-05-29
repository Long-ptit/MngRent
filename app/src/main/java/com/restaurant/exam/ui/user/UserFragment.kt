package com.restaurant.exam.ui.user

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import restaurant.exam.R
import com.restaurant.exam.base.BaseFragment
import restaurant.exam.databinding.FragmentUserBinding
import com.restaurant.exam.ui.home.HomeViewModel
import com.restaurant.exam.ui.login.LoginActivity
import com.restaurant.exam.ui.manage.ManageRestaurantActivity
import com.restaurant.exam.ui.manage_staff.DetailProfileActivity
import com.restaurant.exam.ui.manage_staff.DetailProfileStaffActivity
import com.restaurant.exam.ui.manage_staff.ManageStaffActivity
import com.restaurant.exam.view_model.ViewModelFactory

class UserFragment : BaseFragment<HomeViewModel, FragmentUserBinding>() {
    override fun getContentLayout(): Int {
        return R.layout.fragment_user
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(requireContext()))[HomeViewModel::class.java]
    }

    override fun initView() {
        if (viewModel.checkIsStaff()) {
            binding.layoutHistory.visibility = View.GONE
            binding.layoutRestaurant.visibility = View.GONE
            binding.layoutStaff.visibility = View.GONE
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            requireContext().startActivity(Intent(binding.root.context, LoginActivity::class.java))
            activity?.finish()
        }
    }

    override fun initListener() {

        binding.layoutRestaurant.setOnClickListener {
            var intent =  Intent(
                context,
                ManageRestaurantActivity::class.java
            )
            startActivity(intent)
        }

        binding.layoutStaff.setOnClickListener {
            var intent =  Intent(
                context,
                ManageStaffActivity::class.java
            )
            startActivity(intent)
        }

        binding.layoutAccount.setOnClickListener {
            if (viewModel.checkIsStaff()) {
                var intent =  Intent(
                    context,
                    DetailProfileStaffActivity::class.java
                )
                startActivity(intent)
            } else {
                var intent =  Intent(
                    context,
                    DetailProfileActivity::class.java
                )
                startActivity(intent)
            }
        }


    }

    override fun observerLiveData() {

    }

}
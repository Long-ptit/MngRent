package com.restaurant.exam.ui.statistic

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import restaurant.exam.R
import com.restaurant.exam.base.BaseFragment
import com.restaurant.exam.ui.detail_statistic.DetailStatisticActivity
import restaurant.exam.databinding.FragmentStatisticBinding
import com.restaurant.exam.ui.home.HomeViewModel
import com.restaurant.exam.ui.manage_staff.AddStaffActivity
import com.restaurant.exam.view_model.ViewModelFactory

class StatisticFragment : BaseFragment<HomeViewModel, FragmentStatisticBinding>() {
    override fun getContentLayout(): Int {
        return R.layout.fragment_statistic
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(requireContext()))[HomeViewModel::class.java]
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.btnKhoangNgay.setOnClickListener {
            var intent = Intent(
                context,
                DetailStatisticActivity::class.java
            )
            startActivity(intent)
        }
        viewModel.getStatistic()
    }

    override fun observerLiveData() {

        viewModel.apply {
            statisticResponse.observe(this@StatisticFragment) {
                if (it != null) {
                    binding.tvToday.text = "+ " +  it.totalSumToday.toString() + "đ"
                    binding.tvMonth.text = "+ " + it.totalSumMonth.toString() + "đ"
                    binding.tvWeek.text = "+ " + it.totalSumWeek.toString() + "đ"
                }
            }
        }

    }

}
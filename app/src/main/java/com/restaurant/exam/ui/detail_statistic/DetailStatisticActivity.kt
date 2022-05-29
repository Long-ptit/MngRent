package com.restaurant.exam.ui.detail_statistic

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Bill
import com.restaurant.exam.ui.detail_statistic.adapter.DetailStatisticAdapter
import com.restaurant.exam.ui.manage.adapter.SpinnerAdapterFloor
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutDetailStatisticBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailStatisticActivity :
    BaseActivity<DetailStatisticViewModel, LayoutDetailStatisticBinding>() {

    lateinit var mAdapter: DetailStatisticAdapter

    var from: String = ""
    var to: String = ""

    override fun getContentLayout(): Int {
        return R.layout.layout_detail_statistic
    }

    override fun observerLiveData() {

        viewModel.apply {
            listBillFromToResponse.observe(
                this@DetailStatisticActivity,
                androidx.lifecycle.Observer {
                    if (it != null) {
                        mAdapter.setData(it as ArrayList<Bill>)
                    }
                })
        }

    }

    override fun initView() {
        showError = true

        mAdapter = DetailStatisticAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcv.adapter = mAdapter

    }


    override fun initListener() {
        binding.edtTime.setOnClickListener {
            showDataRangePicker()
        }
        binding.btnGo.setOnClickListener {
            Log.d("ptit", "show spin: " + binding.sp.selectedItemPosition)
            viewModel.getListBill(from, to, binding.sp.selectedItemPosition)
        }
    }

    private fun showDataRangePicker() {

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = TimeConverday2(dateSelected.first)
            val endDate = TimeConverday2(dateSelected.second)
            from = startDate.toString()
            to = endDate.toString();
            binding.edtTime.setText(startDate + " Đến " + endDate)
        }

    }

    fun TimeConverday2(unixTime: Long): String? {
        val date = Date(unixTime)
        // format of the date
        val jdf = SimpleDateFormat("yyyy-MM-dd")
        return jdf.format(date)
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(this))[DetailStatisticViewModel::class.java]
    }
}
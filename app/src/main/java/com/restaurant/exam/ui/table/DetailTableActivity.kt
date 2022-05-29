package com.restaurant.exam.ui.table

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.*
import com.restaurant.exam.ui.add_food_table.AddFoodTableActivity
import com.restaurant.exam.ui.confirm_bill.ConfirmActivity
import com.restaurant.exam.ui.table.adapter.FoodInTableAdapter
import com.restaurant.exam.utils.PREF_RESTAURANT_ID
import com.restaurant.exam.utils.PREF_USER_ID
import com.restaurant.exam.utils.recycleview_utils.GridSpacingItemDecoration
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutDetailTableBinding
import java.io.Serializable

class DetailTableActivity : BaseActivity<DetailTableViewModel, LayoutDetailTableBinding>(), FoodInTableAdapter.IClick {

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, DetailTableActivity::class.java)
        }
    }
    var listFood = arrayListOf<Food>()
    var idFloor: Int = 0
    var tableFirebase:TableFirebase = TableFirebase()
    var sumMoney = 0
    var totalQuantity = 0


    private lateinit var mAdapter : FoodInTableAdapter

    override fun getContentLayout(): Int {
        return R.layout.layout_detail_table
    }

    override fun observerLiveData() {
        viewModel.apply {
            saveResponse.observe(this@DetailTableActivity, androidx.lifecycle.Observer {
                if (it != null) {
                   viewModel.deleteCart(idFloor, tableFirebase)
                    finish();
                }
            })
        }
    }

    override fun initView() {
        showError = true

        if (intent.hasExtra("id_floor")) {
            idFloor  = intent.getIntExtra("id_floor", 0)
            tableFirebase  = intent.getSerializableExtra("table") as TableFirebase
        }

        mAdapter = FoodInTableAdapter(this)

        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)
        binding.rcv.layoutManager = mLayoutManager
        binding.rcv.itemAnimator = DefaultItemAnimator()
        binding.rcv.adapter = mAdapter
        binding.rcv.setHasFixedSize(true)
        binding.rcv.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                25,
                true,
                0
            )
        )
        viewModel.getObserveDetailTable(tableFirebase.id!!, idFloor, tableFirebase.id!!) {
            mAdapter.setList(it)
            listFood = it
            sumMoney = 0
            totalQuantity = 0
            listFood.forEach {
                sumMoney += it.quantity*(it.price)
                totalQuantity += it.quantity
            }
            binding.tvSumMoney.text = sumMoney.toString()
        }
    }

    override fun initListener() {
        binding.btnConfirm.setOnClickListener {
//            showError("Go to confirm bill")
//            var intent =  Intent(
//                this,
//                ConfirmActivity::class.java
//            )
//            intent.putExtra("list", listFood as Serializable)
//            startActivity(intent)
            val foodBill = FoodBill()
            foodBill.data = listFood
            foodBill.bill = Bill(
                totalPrice = sumMoney,
                restaurant = Restaurant(viewModel.dataManager.getInt(PREF_RESTAURANT_ID)),
                staff = Staff(viewModel.dataManager.getInt(PREF_USER_ID)),
                idBan = tableFirebase.id.toString(),
                quantity = totalQuantity
            )
            viewModel.save(foodBill)
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DetailTableViewModel::class.java]
    }

    override fun onClick(food: Food) {
        var intent =  Intent(
        this,
        AddFoodTableActivity::class.java
    )
        intent.putExtra("id_floor", idFloor)
        intent.putExtra("table", tableFirebase)
        startActivity(intent)
    }


}
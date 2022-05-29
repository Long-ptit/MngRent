package com.restaurant.exam.ui.add_food_table

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.TableFirebase
import com.restaurant.exam.ui.add_food_table.adapter.AddFoodAdapter
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.R
import restaurant.exam.databinding.LayoutAddFoodTableBinding

class AddFoodTableActivity : BaseActivity<AddFoodTableViewModel, LayoutAddFoodTableBinding>(), AddFoodAdapter.IClick {

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, AddFoodTableActivity::class.java)
        }
    }

    private lateinit var mAdapter : AddFoodAdapter
    private var idFloor: Int = 0
    private var tableFirebase:TableFirebase = TableFirebase()
    override fun getContentLayout(): Int {
        return R.layout.layout_add_food_table

    }

    override fun observerLiveData() {
        viewModel.apply {
            listFoodResponse.observe(this@AddFoodTableActivity, androidx.lifecycle.Observer {
                if (it != null) {
                   mAdapter.setList(it as ArrayList<Food>)
                }
            })
        }
    }

    override fun initView() {
        if (intent.hasExtra("id_floor")) {
            idFloor  = intent.getIntExtra("id_floor", 0)
            tableFirebase  = intent.getSerializableExtra("table") as TableFirebase
        }

        showError = true
        mAdapter = AddFoodAdapter(this)
        binding.rcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcv.adapter = mAdapter
    }


    override fun initListener() {
        viewModel.getAllFood()
        binding.fab.setOnClickListener {
            var listData = mAdapter.getList()
            listData.forEach {
                viewModel.saveToFirebase("Đồ ăn", it, idFloor, tableFirebase)
            }
            finish()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[AddFoodTableViewModel::class.java]
    }



    override fun onClick(food: Food) {
        TODO("Not yet implemented")
    }

}
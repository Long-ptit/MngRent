package com.restaurant.exam.ui.menu

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import restaurant.exam.R
import com.restaurant.exam.base.BaseFragment
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.ui.add_food_table.adapter.AddFoodAdapter
import com.restaurant.exam.ui.detail_food.AddFoodActivity
import com.restaurant.exam.ui.detail_food.DetailFoodActivity
import restaurant.exam.databinding.FragmentMenuBinding
import com.restaurant.exam.ui.home.HomeViewModel
import com.restaurant.exam.ui.menu.adapter.MenuFoodAdapter
import com.restaurant.exam.ui.table.DetailTableActivity
import com.restaurant.exam.view_model.ViewModelFactory

class MenuFragment : BaseFragment<HomeViewModel, FragmentMenuBinding>(), MenuFoodAdapter.IClick {
    private lateinit var mAdapter : MenuFoodAdapter

    override fun getContentLayout(): Int {
        return R.layout.fragment_menu
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(requireContext()))[HomeViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFood()
    }
    override fun initView() {
        mAdapter = MenuFoodAdapter(this)
        binding.rvHome.adapter = mAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }


    override fun initListener() {
        binding.fab.setOnClickListener {
            var intent = Intent(
                context,
                AddFoodActivity::class.java
            )
            startActivity(intent)
        }

    }

    override fun observerLiveData() {
        viewModel.apply {
            listFoodResponse.observe(this@MenuFragment) {
                if (it != null) {
                    mAdapter.setList(it as ArrayList<Food>)
                }
            }
        }


    }

    override fun onClick(food: Food) {
        var intent = Intent(
            context,
            DetailFoodActivity::class.java
        )
        intent.putExtra("food", food)
        startActivity(intent)
    }

}
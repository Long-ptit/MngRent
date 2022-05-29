package com.restaurant.exam.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.restaurant.exam.ui.add_food_table.AddFoodTableViewModel
import com.restaurant.exam.ui.confirm_bill.ConfirmViewModel
import com.restaurant.exam.ui.detail_food.DetailFoodViewModel
import com.restaurant.exam.ui.detail_statistic.DetailStatisticActivity
import com.restaurant.exam.ui.detail_statistic.DetailStatisticViewModel
import com.restaurant.exam.ui.login.LoginViewModel
import com.restaurant.exam.ui.home.HomeViewModel
import com.restaurant.exam.ui.main.MainViewModel
import com.restaurant.exam.ui.manage.ManageRestaurantViewModel
import com.restaurant.exam.ui.manage_staff.ManageStaffViewModel
import com.restaurant.exam.ui.table.DetailTableViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(context) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        if (modelClass.isAssignableFrom(DetailTableViewModel::class.java)) {
            return DetailTableViewModel() as T
        }
        if (modelClass.isAssignableFrom(AddFoodTableViewModel::class.java)) {
            return AddFoodTableViewModel() as T
        }
        if (modelClass.isAssignableFrom(ConfirmViewModel::class.java)) {
            return ConfirmViewModel() as T
        }
        if (modelClass.isAssignableFrom(DetailFoodViewModel::class.java)) {
            return DetailFoodViewModel() as T
        }
        if (modelClass.isAssignableFrom(ManageRestaurantViewModel::class.java)) {
            return ManageRestaurantViewModel() as T
        }
        if (modelClass.isAssignableFrom(ManageStaffViewModel::class.java)) {
            return ManageStaffViewModel() as T
        }
        if (modelClass.isAssignableFrom(DetailStatisticViewModel::class.java)) {
            return DetailStatisticViewModel() as T
        }


        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
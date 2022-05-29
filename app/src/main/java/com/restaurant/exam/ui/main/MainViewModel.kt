package com.restaurant.exam.ui.main

import android.annotation.SuppressLint
import android.content.Context
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.network.Api
import com.restaurant.exam.view_model.BaseViewModel
import javax.inject.Inject


@SuppressLint("CheckResult")
class MainViewModel(val context: Context) : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api


}
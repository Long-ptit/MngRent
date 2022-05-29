package com.restaurant.exam.ui.detail_statistic

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.Bill
import com.restaurant.exam.data.model.Floor
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.network.Api
import com.restaurant.exam.utils.PREF_RESTAURANT_ID
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@SuppressLint("CheckResult")
class DetailStatisticViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api

    val listBillFromToResponse = MutableLiveData<List<Bill>>()
    @SuppressLint("CheckResult")
    fun getListBill(from: String, to:String, sort: Int) {
        api.getBillResSort(dataManager.getInt(PREF_RESTAURANT_ID), from, to, sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {

                    }
                    listBillFromToResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    fun getIdRestaurant() : Int {
        return dataManager.getInt(PREF_RESTAURANT_ID)
    }

}
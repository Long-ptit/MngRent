package com.restaurant.exam.ui.confirm_bill

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.Bill
import com.restaurant.exam.data.model.FoodBill
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.network.Api
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("CheckResult")
class ConfirmViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api

    val confirmResponse = MutableLiveData<Bill>()
    fun save(request: FoodBill) {
        api.save(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
//                        result.data?.id?.let { dataManager.save(PREF_USER_ID, it) }
//                        dataManager.save(PREF_USER_NAME, result?.data?.hoTen)
//                        dataManager.save(PREF_PHONE_NUMBER, result?.data?.soDienThoai)
//                        dataManager.save(PREF_ADDRESS, result?.data?.diaChi)
                    }
                    confirmResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }
}
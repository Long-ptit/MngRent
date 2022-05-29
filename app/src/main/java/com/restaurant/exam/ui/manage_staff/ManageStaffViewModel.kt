package com.restaurant.exam.ui.manage_staff

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.*
import com.restaurant.exam.network.Api
import com.restaurant.exam.utils.PREF_RESTAURANT_ID
import com.restaurant.exam.utils.PREF_USER_ID
import com.restaurant.exam.utils.PREF_USER_ROLE
import com.restaurant.exam.utils.URL_FIREBASE
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("CheckResult")
class ManageStaffViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api

    val database = Firebase.database(URL_FIREBASE).reference

    val getStaffResponse = MutableLiveData<List<Staff>>()
    fun getStaff() {
        api.getStaffByIdRes(dataManager.getInt(PREF_RESTAURANT_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    getStaffResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val saveStaffResponse = MutableLiveData<Staff>()
    fun saveStaff(data: Staff) {
        api.saveStaff(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    saveStaffResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val saveStaffAdminResponse = MutableLiveData<Staff>()
    fun saveStaffAdmin(data: Staff) {
        api.saveStaffAdmin(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    saveStaffAdminResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val deleteResponse = MutableLiveData<Staff>()
    fun delete(id: Int ) {
        api.deleteStaff(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
//                        result.data?.id?.let { dataManager.save(PREF_USER_ID, it) }
                    }
                    deleteResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val getStaffIDResponse = MutableLiveData<Staff>()
    fun getStaffID( ) {
        api.getStaff(dataManager.getInt(PREF_USER_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
//                        result.data?.id?.let { dataManager.save(PREF_USER_ID, it) }
                    }
                    getStaffIDResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    fun getResId(): Int {
        return dataManager.getInt(PREF_RESTAURANT_ID)
    }

    fun checkIsStaff() : Boolean {
        Log.d("ptit", "checkIsStaff: " + dataManager.getString(PREF_USER_ROLE))
        if (dataManager.getString(PREF_USER_ROLE) != null && dataManager.getString(PREF_USER_ROLE).equals("nhanvien")) {
            return true;
        }
        return false
    }


}
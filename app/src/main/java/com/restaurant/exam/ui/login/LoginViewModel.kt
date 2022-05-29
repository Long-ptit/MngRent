package com.restaurant.exam.ui.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessaging
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.network.Api
import com.restaurant.exam.utils.*
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject



@SuppressLint("CheckResult")
class LoginViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api

    val loginResponse = MutableLiveData<Staff>()
    fun login(loginRequest: Staff) {
        api.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
//                        result.data?.id?.let { dataManager.save(PREF_USER_ID, it) }
                        Log.d("ptit", "login: "+ result.toString())
                        dataManager.save(PREF_USER_NAME, result.username)
                        dataManager.save(PREF_USER_ROLE, result.role)
                        result.id?.let { dataManager.save(PREF_USER_ID, it) }
                        result.restaurant?.id?.let { dataManager.save(PREF_RESTAURANT_ID, it) }
                    }
                    loginResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }


    val signUpResponse = MutableLiveData<Staff>()
    fun signup(loginRequest: Staff) {
        api.signup(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
//                        result.data?.id?.let { dataManager.save(PREF_USER_ID, it) }
                        dataManager.save(PREF_USER_NAME, result.username)
                        dataManager.save(PREF_USER_ROLE, result.role)
                        dataManager.save(PREF_ADDRESS, result.address)
                        dataManager.save(PREF_PASSWORD, result.password)
                        dataManager.save(PREF_RESTAURANT_NAME, result.restaurant?.name)
                        result.id?.let { dataManager.save(PREF_USER_ID, it) }
                        result.restaurant?.id?.let { dataManager.save(PREF_RESTAURANT_ID, it) }
                    }
                    signUpResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    fun initFirebase() {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token: String ->
                FirebaseUtils.saveTokenToFirebase(token, dataManager.getInt(PREF_USER_ID))
                Log.d("DEVICE_TOKEN", token)
            }
    }

    fun setStart(isStart: Boolean) {
        dataManager.save(PREF_START, isStart)
    }

    fun isStart(): Boolean {
        return dataManager.getBoolean(PREF_START)
    }

    fun checkUserLogin() : Boolean {
        return dataManager.getInt(PREF_USER_ID) != -1
    }

    fun checkIsStaff() : Boolean {
        Log.d("ptit", "checkIsStaff: " + dataManager.getString(PREF_USER_ROLE))
        if (dataManager.getString(PREF_USER_ROLE) != null && dataManager.getString(PREF_USER_ROLE).equals("nhanvien")) {
            return true;
        }
        return false
    }

}
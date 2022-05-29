package com.restaurant.exam.ui.manage

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.*
import com.restaurant.exam.network.Api
import com.restaurant.exam.utils.PREF_RESTAURANT_ID
import com.restaurant.exam.utils.URL_FIREBASE
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.internal.Table
import javax.inject.Inject

@SuppressLint("CheckResult")
class ManageRestaurantViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api

    val database = Firebase.database(URL_FIREBASE).reference

    fun saveTableToFirebase(idFloor: Int, idTable: Int, nameTable: String) {
        var tableFirebase = TableFirebase()
        tableFirebase.id = idTable
        tableFirebase.name = nameTable
        tableFirebase.status = "Trống"
        database
            .child("restaurant")
            .child(dataManager.getInt(PREF_RESTAURANT_ID).toString())
            .child(idFloor.toString())
            .child(idTable.toString())
            .child("infor")
            .setValue(tableFirebase)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    fun deleteTable(idFloor: Int, idTable: Int) {
        database
            .child("restaurant")
            .child(dataManager.getInt(PREF_RESTAURANT_ID).toString())
            .child(idFloor.toString())
            .child(idTable.toString())
            .setValue(null)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    val floorResponse = MutableLiveData<List<Floor>>()
    @SuppressLint("CheckResult")
    fun getFloorByRes() {
        api.getFloor(dataManager.getInt(PREF_RESTAURANT_ID))
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
                    floorResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val floorSaveResponse = MutableLiveData<Floor>()
    fun saveFloor(request: Floor) {
        api.saveFloor(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    floorSaveResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val tableSaveResponse = MutableLiveData<TableRestaurant>()
    fun saveTable(request: TableRestaurant) {
        api.saveTable(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    tableSaveResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    val getTableResponse = MutableLiveData<List<TableRestaurant>>()
    fun getTable(idFloor: Int) {
        api.getTableByIdFloor(idFloor)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    getTableResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

    fun getResId(): Int {
        return dataManager.getInt(PREF_RESTAURANT_ID)
    }


}
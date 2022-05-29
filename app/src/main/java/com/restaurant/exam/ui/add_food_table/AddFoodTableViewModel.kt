package com.restaurant.exam.ui.add_food_table

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.TableFirebase
import com.restaurant.exam.network.Api
import com.restaurant.exam.utils.DANG_AN
import com.restaurant.exam.utils.PREF_RESTAURANT_ID
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("CheckResult")
class AddFoodTableViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api
    val database = Firebase.database("https://restaurant-dd83b-default-rtdb.firebaseio.com/").reference

    fun saveToFirebase(type: String, data: Food, idFloor: Int, tableFirebase: TableFirebase) {
        database
            .child("restaurant")
            .child(dataManager.getInt(PREF_RESTAURANT_ID).toString())
            .child(idFloor.toString())
            .child(tableFirebase.id.toString())
            .child("data")
            .child(data.id.toString())
            .setValue(data)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }

        tableFirebase.status = DANG_AN
        database
            .child("restaurant")
            .child(dataManager.getInt(PREF_RESTAURANT_ID).toString())
            .child(idFloor.toString())
            .child(tableFirebase.id.toString())
            .child("infor")
            .setValue(tableFirebase)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    val listFoodResponse = MutableLiveData<List<Food>>()
    @SuppressLint("CheckResult")
    fun getAllFood() {
        api.getFood(dataManager.getInt(PREF_RESTAURANT_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
                    }
                    listFoodResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }

}
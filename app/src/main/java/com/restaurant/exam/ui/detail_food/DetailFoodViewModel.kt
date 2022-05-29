package com.restaurant.exam.ui.detail_food

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.DataManager
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
class DetailFoodViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api
    val database = Firebase.database("https://restaurant-dd83b-default-rtdb.firebaseio.com/").reference

    fun saveToFirebase(type: String, data: Food) {
        val restaurant: Restaurant = Restaurant()
        restaurant.address = "haha"
        database
            .child("restaurant")
            .child("1")
            .child("1")
            .child("1")
            .child("data")
            .child(type)
            .child(data.id.toString())
            .setValue(data)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    val testResponse = MutableLiveData<Food>()
    fun save(req1: MultipartBody.Part,
             req2: RequestBody,
             req3: RequestBody,
             req4: RequestBody,
             req5: RequestBody,
             req6: RequestBody ) {
        api.saveFood(req1, req2, req3, req4, req5,req6)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result ->
                    if (result != null) {
//                        result.data?.id?.let { dataManager.save(PREF_USER_ID, it) }

                    }
                    testResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }


    val deleteResponse = MutableLiveData<Food>()
    fun delete(id: Int ) {
        api.deleteFood(id)
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

    fun getIdRestaurant() : Int {
        return dataManager.getInt(PREF_RESTAURANT_ID)
    }

}
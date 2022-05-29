package com.restaurant.exam.ui.table

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.DataManager
import com.restaurant.exam.data.model.Bill
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.FoodBill
import com.restaurant.exam.data.model.TableFirebase
import com.restaurant.exam.network.Api
import com.restaurant.exam.utils.PREF_RESTAURANT_ID
import com.restaurant.exam.utils.TRONG
import com.restaurant.exam.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("CheckResult")
class DetailTableViewModel : BaseViewModel() {
    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var api: Api
    var valueEventListener: ValueEventListener? = null
    var tableDatabase: DatabaseReference? = null
    val database =
        Firebase.database("https://restaurant-dd83b-default-rtdb.firebaseio.com/").reference

    fun getObserveDetailTable(id: Int,idFloor: Int, idTable: Int, callbackSuccess: (list: ArrayList<Food>) -> Unit) {
        if (valueEventListener != null && tableDatabase != null) {
            tableDatabase!!.removeEventListener(valueEventListener!!)
        }
        tableDatabase = database
            .child("restaurant")
            .child(dataManager.getInt(PREF_RESTAURANT_ID).toString())
            .child(idFloor.toString())
            .child(idTable.toString())
            .child("data")

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listWishlistAdd = ArrayList<Food>()
                for (dataSnapshot in snapshot.children) {
                    val wishlistAdd = dataSnapshot.getValue(Food::class.java)
                    wishlistAdd?.let { listWishlistAdd.add(it) }
                    Log.d("ptit", dataSnapshot.toString())
                }
                Log.d("ptit", "size sau khi + ${listWishlistAdd.size}")
                callbackSuccess.invoke(listWishlistAdd)
            }

            override fun onCancelled(error: DatabaseError) {
                tableDatabase!!.removeEventListener(this)
            }
        }
        tableDatabase!!.addValueEventListener(valueEventListener!!)
    }

    fun deleteCart(idFloor: Int, tableFirebase: TableFirebase) {
        database
            .child("restaurant")
            .child(dataManager.getInt(PREF_RESTAURANT_ID).toString())
            .child(idFloor.toString())
            .child(tableFirebase.id.toString())
            .child("data")
            .setValue(null)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }

        tableFirebase.status = TRONG
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

    val saveResponse = MutableLiveData<Bill>()
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
                    saveResponse.postValue(result)
                },
                { throwable ->
                    handleApiError(throwable)
                }
            )
    }


}
package com.restaurant.exam.utils

import android.util.Log
import com.acom.service.user.fcm.INotificationService
import com.acom.service.user.fcm.PushFCM
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.restaurant.exam.data.firebase.SendNotificationRequest
import com.restaurant.exam.data.firebase.SendNotificationResponse
import com.restaurant.exam.data.firebase.TokenFCM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FirebaseUtils {
    private val database = Firebase.database(URL_FIREBASE).reference

    fun saveTokenToFirebase(deviceTokenFCM: String, userId: Int) {
        val token = TokenFCM(deviceTokenFCM)
        database.child(NODE_SAVE_FCM_TOKEN).child(userId.toString()).setValue(token)
            .addOnSuccessListener {
                Log.d("SAVE_FCM_TOKEN", "success")
            }
            .addOnFailureListener {
                Log.d("SAVE_FCM_TOKEN", "failure")
            }
    }


    fun pushNotification(
        userReceiverId: Int,
        objectDataNotification: Any,
        callbackSuccess: (response: Response<SendNotificationResponse>) -> Unit,
        callbackFailure: (error: Throwable) -> Unit
    ) {
        val nodeTokenFCM = database.child(NODE_SAVE_FCM_TOKEN)
        val query: Query = nodeTokenFCM.orderByKey().equalTo(userReceiverId.toString())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val token: TokenFCM? = dataSnapshot.getValue(TokenFCM::class.java)
                    PushFCM()
                        .push()
                        .create(INotificationService::class.java)
                        .sendNotification(SendNotificationRequest(to = token?.token?:"", data = objectDataNotification))
                        .enqueue(object : Callback<SendNotificationResponse> {
                            override fun onResponse(
                                call: Call<SendNotificationResponse>,
                                response: Response<SendNotificationResponse>
                            ) {
                                database.child(NODE_SAVE_NOTIFICATION).child(userReceiverId.toString()).child("${System.currentTimeMillis()} send $userReceiverId").setValue(objectDataNotification)
                                callbackSuccess.invoke(response)
                                Log.d("PUSH_NOTIFICATION", "status: success")
                                Log.d("PUSH_NOTIFICATION", "token: ${token?.token}")
                            }

                            override fun onFailure(call: Call<SendNotificationResponse>, t: Throwable) {
                                callbackFailure.invoke(t)
                                Log.d("PUSH_NOTIFICATION", "status: failure")
                                Log.d("PUSH_NOTIFICATION", "token: ${token?.token}")
                            }

                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

//    fun getNotification(idUser : Int, callbackSuccess: (list: ArrayList<NotificationResponse>) -> Unit){
//        val nodeNotification = database.child(BuildConfig.NODE_SAVE_NOTIFICATION).child(idUser.toString())
//        nodeNotification.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val listNotification = ArrayList<NotificationResponse>()
//                for (dataSnapshot in snapshot.children) {
//                    val notification = dataSnapshot.getValue(NotificationResponse::class.java)
//                    notification?.let { listNotification.add(it) }
//                    Log.d("DATA_NOTIFICATION", listNotification.size.toString())
//                }
//                listNotification.reverse()
//                callbackSuccess.invoke(listNotification)
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
//    }


}
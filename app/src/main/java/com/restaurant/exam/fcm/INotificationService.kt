package com.acom.service.user.fcm


import com.restaurant.exam.data.firebase.SendNotificationRequest
import com.restaurant.exam.data.firebase.SendNotificationResponse
import com.restaurant.exam.utils.SERVER_KEY_FCM
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface INotificationService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=${SERVER_KEY_FCM}"
    )
    @POST("fcm/send")
    fun sendNotification(@Body request: SendNotificationRequest): Call<SendNotificationResponse>
}
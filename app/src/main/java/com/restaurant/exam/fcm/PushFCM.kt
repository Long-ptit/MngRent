package com.acom.service.user.fcm
import com.restaurant.exam.utils.API_URL_GOOGLE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PushFCM {
    private var retrofit: Retrofit? = null
    fun push(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(API_URL_GOOGLE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
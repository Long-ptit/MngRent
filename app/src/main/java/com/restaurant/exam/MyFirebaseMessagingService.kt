package com.restaurant.exam

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.restaurant.exam.ui.splash.SplashActivity
import com.restaurant.exam.utils.FirebaseUtils
import com.restaurant.exam.utils.KEY_BODY
import com.restaurant.exam.utils.KEY_TITLE
import com.restaurant.exam.utils.NotificationUtils


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {


    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleNotification(remoteMessage)
    }

    private fun handleNotification(remoteMessage: RemoteMessage){
        val remoteMessageData = remoteMessage.data
        Log.d("ptit", "data: " + remoteMessageData)
        val title = remoteMessageData[KEY_TITLE]
        val body = remoteMessageData[KEY_BODY]
        val notificationUtils = NotificationUtils(applicationContext)
        val intent = Intent(this, SplashActivity::class.java)
        notificationUtils.showNotificationMessage(
            title,
            body,
            System.currentTimeMillis().toString(), intent
        )
    }

//    override fun onNewToken(token: String) {
////        EventBus.getDefault().post(MessageEvent(SENT_TOKEN_FCM, token))
//        FirebaseUtils.saveTokenToFirebase(token, 2)
//    }

}

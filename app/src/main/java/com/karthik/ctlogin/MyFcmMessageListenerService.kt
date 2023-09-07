package com.karthik.ctlogin

import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService


class MyFcmMessageListenerService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
//        CTFcmMessageHandler()
//            .createNotification(applicationContext, message)

        message.data.apply {
            try {
                if (isNotEmpty()) {
                    val extras = Bundle()
                    for ((key, value) in this) {
                        extras.putString(key, value)
                    }
                    val info = CleverTapAPI.getNotificationInfo(extras)
                    if (info.fromCleverTap) {
//                        CleverTapAPI.createNotification(applicationContext, extras)
                        CTFcmMessageHandler()
                            .createNotification(applicationContext, message)
                    } else {
                        // not from CleverTap handle yourself or pass to another provider
                    }
                }
            } catch (t: Throwable) {
                Log.d("MYFCMLIST", "Error parsing FCM message", t)
            }
        }


    }
}
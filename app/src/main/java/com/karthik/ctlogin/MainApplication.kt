package com.karthik.ctlogin

import java.util.*
import android.content.Context
import android.util.Log
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import android.app.Application
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener
import io.branch.referral.Branch

// class MainApplication : Application(), CTPushNotificationListener{
class MainApplication : Application(), CTPushNotificationListener{

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()

        // CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        CleverTapAPI.setDebugLevel(3);
//        CleverTapAPI.createNotificationChannelGroup(this, "YourGroupId", "YourGroupName")
        CleverTapAPI.createNotificationChannel(
            applicationContext,
            "testkk123",
            "test",
            "test",
            NotificationManager.IMPORTANCE_MAX,
            true
        )

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler);

        // val cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext)
        // cleverTapAPI!!.ctPushNotificationListener = this

        Branch.enableLogging()
        Branch.getAutoInstance(this)
    }

    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>?) {
        println(payload)
    }

}
package com.karthik.ctlogin

import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.messaging.FirebaseMessaging
import com.karthik.ctlogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityLifecycleCallback.register(application)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG)
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CleverTapAPI.createNotificationChannelGroup(
                applicationContext,
                "testkk1",
                "Notification Test"
            )
        }
        CleverTapAPI.createNotificationChannel(
            applicationContext, "testkk123", "Notification Test", "CleverTap Notification Test",
            NotificationManager.IMPORTANCE_MAX, true
        )
        CleverTapAPI.createNotificationChannel(
            applicationContext,
            "testkk1234",
            "KK Notification Test",
            "KK CleverTap Notification Test",
            NotificationManager.IMPORTANCE_MAX,
            true
        )

        val email = intent.extras?.getString("email")
        val identity = intent.extras?.getString("identity")
        val phone = intent.extras?.getString("phone")
        val name = intent.extras?.getString("name")

        val profile = HashMap<String, Any>()
        profile["Name"] = name!!
        profile["Identity"] = identity!!
        profile["Email"] = email!!
        profile["Phone"] = phone!!
        profile["MSG-email"] = true
        profile["MSG-push"] = true
        profile["MSG-sms"] = true
        profile["MSG-whatsapp"] = true
        CleverTapAPI.getDefaultInstance(applicationContext)?.onUserLogin(profile)
        Toast.makeText(applicationContext, "Welcome", Toast.LENGTH_SHORT).show()

        //Custom Android Push Notifications Handling
        FirebaseMessaging.getInstance().token.addOnSuccessListener { res->
            if(res != null) {
                println("res= $res")
                cleverTapDefaultInstance?.pushFcmRegistrationId(res, true)
            }
        }

        binding.pushEvent.setOnClickListener {
            val prodViewedAction = mapOf(
                "Product Name" to "Casio Chronograph Watch",
                "Category" to "Mens Accessories",
                "Price" to 59.99
            )
            cleverTapDefaultInstance?.pushEvent("Product Custom Viewed", prodViewedAction)
            Toast.makeText(applicationContext, "Event Clicked!", Toast.LENGTH_SHORT).show()
        }

    }
}
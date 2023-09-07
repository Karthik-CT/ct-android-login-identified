package com.karthik.ctlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler
//import com.clevertap.android.sdk.CleverTapAPI
import com.karthik.ctlogin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CleverTapAPI.getDefaultInstance(applicationContext)?.getCleverTapID {
//            println("CTID: $it")
//        }

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler);

        binding.updateProfile.setOnClickListener {
        }

        binding.onUserLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val identity = binding.identity.text.toString()
            val phone = binding.phoneNumber.text.toString()
            val name = binding.name.text.toString()
            startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("email", email)
                putExtra("identity", identity)
                putExtra("phone", phone)
                putExtra("name", name)
            })
            finish()
            Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
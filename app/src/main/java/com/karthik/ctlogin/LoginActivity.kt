package com.karthik.ctlogin

//import com.clevertap.android.sdk.CleverTapAPI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.karthik.ctlogin.databinding.ActivityLoginBinding
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener
import io.branch.referral.BranchError
import io.branch.referral.util.BRANCH_STANDARD_EVENT
import io.branch.referral.util.BranchEvent
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    object branchListener : Branch.BranchReferralInitListener {
        override fun onInitFinished(referringParams: JSONObject?, error: BranchError?) {
            if (error == null) {
                Log.i("BRANCH SDK", referringParams.toString())
            } else {
                Log.e("BRANCH SDK", error.message)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Branch.enableLogging()

        val clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
            applicationContext
        )
        clevertapDefaultInstance?.getCleverTapID { cleverTapID -> // Callback on main thread
            val branch = Branch.getInstance()
            branch.setRequestMetadata(
                "\$clevertap_attribution_id",
                cleverTapID
            )
            Log.e("cid", cleverTapID)
        }
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        BranchEvent(BRANCH_STANDARD_EVENT.SEARCH)
            .setCustomerEventAlias("find")
            .setDescription("Product Search")
            .setSearchQuery("Nike")
            .addCustomDataProperty("Jersey", "PSG")
            .logEvent(this)

        BranchEvent("BRanchTest")
            .setCustomerEventAlias("find")
            .setDescription("Product Search")
            .setSearchQuery("Nike")
            .addCustomDataProperty("Jersey", "PSG")
            .logEvent(this)
    }

    override fun onStart() {
        super.onStart()
        // Branch init
        Branch.sessionBuilder(this).withCallback(branchListener).withData(this.intent?.data).init()
    }
}
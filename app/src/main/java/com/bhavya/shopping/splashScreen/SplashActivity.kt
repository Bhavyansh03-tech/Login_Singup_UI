package com.bhavya.shopping.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bhavya.shopping.R
import com.bhavya.shopping.onboarding.OnboardingHolder
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler().postDelayed({
            if (user != null){
                startActivity(Intent(this@SplashActivity, OnboardingHolder::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashActivity, OnboardingHolder::class.java))
                finish()
            }
        }, 2000)
    }
}
package com.bhavya.shopping.customerOnbording

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.bhavya.shopping.R
import com.bhavya.shopping.customerLoginSignup.CustomerLogin
import com.bhavya.shopping.customerLoginSignup.CustomerSignup
import com.google.android.material.button.MaterialButton

class CustomerOnbording3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_customer_onbording3, container, false)

        val login : MaterialButton = v.findViewById(R.id.login)
        val signup : MaterialButton = v.findViewById(R.id.signup)
        val lottie : LottieAnimationView = v.findViewById(R.id.lottie_3)
        val heading : TextView = v.findViewById(R.id.onboard_heading_3)
        val bio : TextView = v.findViewById(R.id.onboard_bio_3)

        // Nxt to login screen :->
        login.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, CustomerLogin::class.java))
                finish()
            }
        }

        // Nxt to Signup screen :->
        signup.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, CustomerSignup::class.java))
                finish()
            }
        }

        // Animation :->
        login.translationX = -300f
        signup.translationX = 300f
        lottie.translationX = 300f
        heading.translationX = 300f
        bio.translationX = 300f

        login.alpha = 0f
        signup.alpha = 0f
        lottie.alpha = 0f
        heading.alpha = 0f
        bio.alpha = 0f

        lottie.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        heading.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        bio.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        login.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()
        signup.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()

        return v
    }
}
package com.bhavya.shopping.adminLoginSignup

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.airbnb.lottie.LottieAnimationView
import com.bhavya.shopping.R
import com.bhavya.shopping.onboarding.OnboardingHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AdminLogin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // Layout Initializing :->
        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        ref = FirebaseDatabase.getInstance().getReference("Admin")

        // Layout Initializing :->
        val emailLayout = findViewById<TextInputLayout>(R.id.email_layout)
        val passLayout = findViewById<TextInputLayout>(R.id.pass_layout)

        // Lottie Initializing :->
        val flower1 = findViewById<LottieAnimationView>(R.id.flower_1)
        val flower2 = findViewById<LottieAnimationView>(R.id.flower_2)
        val flower3 = findViewById<LottieAnimationView>(R.id.flower_3)
        val plant = findViewById<LottieAnimationView>(R.id.anim_2)

        // Btn Initializing :->
        val back = findViewById<ImageView>(R.id.admin_back_btn)
        val login = findViewById<MaterialButton>(R.id.login_btn)

        // Text Initializing :->
        val heading = findViewById<TextView>(R.id.heading)
        val accountTxt = findViewById<TextView>(R.id.txt)
        val forgot = findViewById<TextView>(R.id.forgot_password)
        val signUpTxt = findViewById<TextView>(R.id.signup_txt)

        // Taking Lines For Animation:->
        val line1 = findViewById<View>(R.id.email_line)
        val line2 = findViewById<View>(R.id.pass_line)

        // Animation :->
        flower1.translationX = 300f
        flower2.translationX = 300f
        flower3.translationX = 300f
        back.translationX = -300f
        heading.translationX = -300f
        emailLayout.translationX = 300f
        line1.translationX = 300f
        passLayout.translationX = 300f
        line2.translationX = 300f
        forgot.translationX = 300f
        login.translationX = 300f
        accountTxt.translationX = -300f
        signUpTxt.translationX = 300f
        plant.translationX = -300f

        flower1.alpha = 0f
        flower2.alpha = 0f
        flower3.alpha = 0f
        back.alpha = 0f
        heading.alpha = 0f
        emailLayout.alpha = 0f
        passLayout.alpha = 0f
        forgot.alpha = 0f
        login.alpha = 0f
        accountTxt.alpha = 0f
        signUpTxt.alpha = 0f
        plant.alpha = 0f
        line1.alpha = 0f
        line2.alpha = 0f

        flower1.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(100).start()
        flower2.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(200).start()
        flower3.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(300).start()
        back.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(300).start()
        heading.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        emailLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        line1.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        passLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        line2.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        forgot.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()
        login.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        accountTxt.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(900).start()
        signUpTxt.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(950).start()
        plant.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(1000).start()

        // Functioning Button's :->
        signUpTxt.setOnClickListener {
            startActivity(Intent(this@AdminLogin, AdminSignup::class.java))
            finish()
        }
        back.setOnClickListener {
            startActivity(Intent(this@AdminLogin, OnboardingHolder::class.java))
            finish()
        }
        forgot.setOnClickListener {
            startActivity(Intent(this@AdminLogin, AdminForgotPassword::class.java))
        }

        // Functioning Btn to get logged in :->
        login.setOnClickListener {

            // Layout Edit Text Initializing :->
            val email = findViewById<TextInputEditText>(R.id.email)
            val pass = findViewById<TextInputEditText>(R.id.pass)

            // Edit Text Into String :->
            val emailStr = email.text.toString().trim()
            val passStr = pass.text.toString().trim()

            if (emailStr.isEmpty() || passStr.isEmpty()){
                if (emailStr.isEmpty()){
                    emailLayout.error = "Required*"
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (passStr.isEmpty()){
                    passLayout.error = "Required*"
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                Toast.makeText(this, "Invalid Details!", Toast.LENGTH_SHORT).show()
            }else if (!emailStr.matches(emailPattern.toRegex())){
                emailLayout.error = "Enter valid email address"
                email.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                Toast.makeText(this, "Enter Valid email", Toast.LENGTH_SHORT).show()
            }else {
                auth.signInWithEmailAndPassword(emailStr, passStr).addOnCompleteListener{
                    if (it.isSuccessful){
                        val dialog = Dialog(this)
                        dialog.setContentView(R.layout.dialog_loading)
                        dialog.show()
                        Toast.makeText(this, "Home Screen", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
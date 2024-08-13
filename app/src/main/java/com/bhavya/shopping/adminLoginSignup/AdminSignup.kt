package com.bhavya.shopping.adminLoginSignup

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.airbnb.lottie.LottieAnimationView
import com.bhavya.shopping.R
import com.bhavya.shopping.aminInfo.AdminInfo
import com.bhavya.shopping.onboarding.OnboardingHolder
import com.bhavya.shopping.users.AdminUsers
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker

class AdminSignup : AppCompatActivity() {

    companion object{
        private const val Req_Code = 120
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_signup)

        // Layout Initializing :->
        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        ref = FirebaseDatabase.getInstance().getReference("Admin")

        // Layout Initializing :->
        val nameLayout = findViewById<TextInputLayout>(R.id.name_layout)
        val companyNameLayout = findViewById<TextInputLayout>(R.id.company_layout)
        val emailLayout = findViewById<TextInputLayout>(R.id.email_layout)
        val phoneLayout = findViewById<TextInputLayout>(R.id.phone_layout)
        val addressLayout = findViewById<TextInputLayout>(R.id.address_layout)
        val passLayout = findViewById<TextInputLayout>(R.id.pass_layout)
        val conPassLayout = findViewById<TextInputLayout>(R.id.con_pass_layout)
        val countryCodePicker: CountryCodePicker = findViewById(R.id.CountryCodePicker)

        // Lottie Initializing :->
        val flower1 = findViewById<LottieAnimationView>(R.id.flower_1)
        val flower2 = findViewById<LottieAnimationView>(R.id.flower_2)
        val flower3 = findViewById<LottieAnimationView>(R.id.flower_3)
        val plant = findViewById<LottieAnimationView>(R.id.anim_2)

        // Btn Initializing :->
        val back = findViewById<ImageView>(R.id.admin_back_btn2)
        val signUp = findViewById<MaterialButton>(R.id.signup_btn)
        val google = findViewById<MaterialButton>(R.id.google_btn)

        // Text Initializing :->
        val heading = findViewById<TextView>(R.id.heading)
        val accountTxt = findViewById<TextView>(R.id.txt)
        val or = findViewById<LinearLayout>(R.id.or)
        val loginTxt = findViewById<TextView>(R.id.login_txt)
        val line = findViewById<View>(R.id.line)

        // Taking Lines For Animation:->
        val line1 = findViewById<View>(R.id.name_line)
        val line2 = findViewById<View>(R.id.company_line)
        val line3 = findViewById<View>(R.id.email_line)
        val line4 = findViewById<View>(R.id.phone_line)
        val line5 = findViewById<View>(R.id.country_line)
        val line6 = findViewById<View>(R.id.address_line)
        val line7 = findViewById<View>(R.id.pass_line)
        val line8 = findViewById<View>(R.id.conPass_line)

        // Animation :->
        flower1.translationX = 300f
        flower2.translationX = 300f
        flower3.translationX = 300f
        back.translationX = -300f
        heading.translationX = -300f
        nameLayout.translationX = 300f
        line1.translationX = 300f
        companyNameLayout.translationX = 300f
        line2.translationX = 300f
        emailLayout.translationX = 300f
        line3.translationX = 300f
        phoneLayout.translationX = 300f
        line4.translationX = 300f
        countryCodePicker.translationX = -300f
        line5.translationX = -300f
        line.translationY = 300f
        addressLayout.translationX = 300f
        line6.translationX = 300f
        passLayout.translationX = 300f
        line7.translationX = 300f
        conPassLayout.translationX = 300f
        line8.translationX = 300f
        signUp.translationY = 300f
        or.translationY = 300f
        google.translationY = 300f
        accountTxt.translationX = -300f
        loginTxt.translationX = 300f
        plant.translationX = -300f

        flower1.alpha = 0f
        flower2.alpha = 0f
        flower3.alpha = 0f
        back.alpha = 0f
        heading.alpha = 0f
        nameLayout.alpha = 0f
        companyNameLayout.alpha = 0f
        emailLayout.alpha = 0f
        phoneLayout.alpha = 0f
        line.alpha = 0f
        countryCodePicker.alpha = 0f
        addressLayout.alpha = 0f
        passLayout.alpha = 0f
        conPassLayout.alpha = 0f
        signUp.alpha = 0f
        or.alpha = 0f
        google.alpha = 0f
        accountTxt.alpha = 0f
        loginTxt.alpha = 0f
        plant.alpha = 0f
        line1.alpha = 0f
        line2.alpha = 0f
        line3.alpha = 0f
        line4.alpha = 0f
        line5.alpha = 0f
        line6.alpha = 0f
        line7.alpha = 0f
        line8.alpha = 0f

        flower1.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(100).start()
        flower2.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(200).start()
        flower3.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(300).start()
        back.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(300).start()
        heading.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        nameLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        line1.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        companyNameLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        line2.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        emailLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()
        line3.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()
        phoneLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        line4.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        line.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        countryCodePicker.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        line5.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        addressLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(900).start()
        line6.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(900).start()
        passLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(950).start()
        line7.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(950).start()
        conPassLayout.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(1000).start()
        line8.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(1000).start()
        signUp.animate().translationY(0f).alpha(1f).setDuration(1500).setStartDelay(1100).start()
        or.animate().translationY(0f).alpha(1f).setDuration(1500).setStartDelay(1150).start()
        google.animate().translationY(0f).alpha(1f).setDuration(1500).setStartDelay(1200).start()
        accountTxt.animate().translationX(0f).alpha(1f).setDuration(1500).setStartDelay(1250).start()
        loginTxt.animate().translationX(0f).alpha(1f).setDuration(1500).setStartDelay(1300).start()
        plant.animate().translationX(0f).alpha(1f).setDuration(1500).setStartDelay(1400).start()

        // Functioning Button's :->
        loginTxt.setOnClickListener {
            startActivity(Intent(this@AdminSignup, AdminLogin::class.java))
            finish()
        }
        back.setOnClickListener {
            startActivity(Intent(this@AdminSignup, OnboardingHolder::class.java))
            finish()
        }

        // Functioning Google Btn :->
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        google.setOnClickListener {
            Toast.makeText(this, "Logging In...", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }

        // Signing Up Function :->
        signUp.setOnClickListener {

            // Layout Edit Text Initializing :->
            val name = findViewById<TextInputEditText>(R.id.name)
            val companyName = findViewById<TextInputEditText>(R.id.company)
            val email = findViewById<TextInputEditText>(R.id.email)
            val phone = findViewById<TextInputEditText>(R.id.phone)
            val address = findViewById<TextInputEditText>(R.id.address)
            val pass = findViewById<TextInputEditText>(R.id.pass)
            val conPass = findViewById<TextInputEditText>(R.id.con_pass)

            // Edit Text Into String :->
            val nameStr = name.text.toString().trim()
            val companyNameStr = companyName.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val phoneStr = phone.text.toString().trim()
            val addressStr = address.text.toString().trim()
            val passStr = pass.text.toString().trim()
            val conPassStr = conPass.text.toString().trim()

            if (nameStr.isEmpty() || companyNameStr.isEmpty() || emailStr.isEmpty() || phoneStr.isEmpty() || addressStr.isEmpty() || passStr.isEmpty() || conPassStr.isEmpty()){
                if (nameStr.isEmpty()){
                    nameLayout.error = "Required*"
                    name.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (companyNameStr.isEmpty()){
                    companyNameLayout.error = "Required*"
                    companyName.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (emailStr.isEmpty()){
                    emailLayout.error = "Required*"
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (phoneStr.isEmpty()){
                    phoneLayout.error = "Required*"
                    phone.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (addressStr.isEmpty()){
                    addressLayout.error = "Required*"
                    address.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (passStr.isEmpty()){
                    passLayout.error = "Required*"
                    pass.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                if (conPassStr.isEmpty()){
                    conPassLayout.error = "Required*"
                    conPass.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                }
                Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show()
            }else if (!emailStr.matches(emailPattern.toRegex())){
                emailLayout.error = "Enter valid email address"
                email.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            }else if (phoneStr.length<10){
                phoneLayout.error = "Number should be 10 in length"
                phone.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                Toast.makeText(this, "Enter Valid number", Toast.LENGTH_SHORT).show()
            }else if (passStr.length < 6){
                passLayout.error = "Enter more than 6 character"
                pass.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                Toast.makeText(this, "Enter Valid length", Toast.LENGTH_SHORT).show()
            }else if (passStr != conPassStr){
                conPassLayout.error = "Password not matched"
                conPass.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_error_edit_text, null)
                Toast.makeText(this, "Enter Valid pass", Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            val phoneNumber = countryCodePicker.selectedCountryCodeWithPlus + phoneStr
                            addUserToDatabase(nameStr, companyNameStr, emailStr, passStr, phoneNumber, addressStr, auth.currentUser?.uid!!)
                            val dialog = Dialog(this@AdminSignup)
                            dialog.setContentView(R.layout.dialog_loading)
                            dialog.show()
                        }else{
                            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun addUserToDatabase(
        nameStr: String,
        companyNameStr: String,
        emailStr: String,
        passStr: String,
        phoneNumber: String,
        addressStr: String,
        uid: String
    ) {
        val adminUser = AdminUsers(nameStr, companyNameStr, emailStr, passStr, uid, phoneNumber, addressStr)
        ref.child(companyNameStr).setValue(adminUser).addOnCompleteListener {
            Toast.makeText(this, "Home Screen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Req_Code){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }catch (e: ApiException){
                    Log.w("SignInActivity", exception.toString())
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    Toast.makeText(baseContext, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            startActivity(Intent(this@AdminSignup, AdminInfo::class.java))
            finish()
        }
    }
}
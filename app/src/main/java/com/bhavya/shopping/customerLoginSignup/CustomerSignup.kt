package com.bhavya.shopping.customerLoginSignup

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bhavya.shopping.R
import com.bhavya.shopping.onboarding.OnboardingHolder
import com.bhavya.shopping.users.Users
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
import com.google.firebase.database.*
import com.hbb20.CountryCodePicker

class CustomerSignup : AppCompatActivity() {

    companion object{
        private const val Req_Code = 120
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var ref: DatabaseReference
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Firebase Initializing :->
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("users")

        // Layout Initializing :->
        val firstLayout = findViewById<TextInputLayout>(R.id.name_txt_layout)
        val lastLayout = findViewById<TextInputLayout>(R.id.last_name_txt_layout)
        val emailLayout = findViewById<TextInputLayout>(R.id.email_layout)
        val phoneLayout = findViewById<TextInputLayout>(R.id.phone_txt_layout)
        val passLayout = findViewById<TextInputLayout>(R.id.pass_layout)
        val conPassLayout = findViewById<TextInputLayout>(R.id.con_pass_layout)
        val countryCodePicker: CountryCodePicker = findViewById(R.id.CountryCodePicker)

        // Btn Initializing :->
        val signupBtn = findViewById<MaterialButton>(R.id.signup_btn)
        val google = findViewById<MaterialButton>(R.id.google_btn)
        val back = findViewById<MaterialButton>(R.id.back_btn)

        // Text Initializing :->
        val headingTxt = findViewById<TextView>(R.id.txt)
        val account = findViewById<TextView>(R.id.text)
        val loginTxt = findViewById<TextView>(R.id.login_txt)
        val or = findViewById<LinearLayout>(R.id.or)
        val line = findViewById<View>(R.id.line)

        // Back Btn Functioning :->
        back.setOnClickListener {
            startActivity(Intent(this@CustomerSignup, OnboardingHolder::class.java))
            finish()
        }

        // Functioning txt to the Login page :->
        loginTxt.setOnClickListener {
            startActivity(Intent(this@CustomerSignup, CustomerLogin::class.java))
            finish()
        }

        // Animation :->
        firstLayout.translationX = -300f
        lastLayout.translationX = 300f
        emailLayout.translationX = 300f
        phoneLayout.translationX = 300f
        countryCodePicker.translationX = -300f
        passLayout.translationX = 300f
        conPassLayout.translationX = 300f
        signupBtn.translationY = 300f
        google.translationY = 300f
        back.translationX = -300f
        headingTxt.translationX = -300f
        account.translationX = -300f
        loginTxt.translationX = 300f
        or.translationY = 300f
        line.translationY = 300f

        firstLayout.alpha = 0f
        lastLayout.alpha = 0f
        emailLayout.alpha = 0f
        phoneLayout.alpha = 0f
        countryCodePicker.alpha = 0f
        passLayout.alpha = 0f
        conPassLayout.alpha = 0f
        signupBtn.alpha = 0f
        google.alpha = 0f
        back.alpha = 0f
        headingTxt.alpha = 0f
        account.alpha = 0f
        loginTxt.alpha = 0f
        or.alpha = 0f
        line.alpha = 0f

        back.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        headingTxt.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        firstLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(400).start()
        lastLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(450).start()
        emailLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(500).start()
        phoneLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(600).start()
        line.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        countryCodePicker.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(650).start()
        passLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(700).start()
        conPassLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(800).start()
        signupBtn.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(900).start()
        google.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(950).start()
        or.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(1000).start()
        account.animate().translationX(0f).alpha(1f).setDuration(1100).setStartDelay(1100).start()
        loginTxt.animate().translationX(0f).alpha(1f).setDuration(1100).setStartDelay(1100).start()

        // Functioning Btn to register info in database :->
        signupBtn.setOnClickListener {

            // Layout Edit Text Initializing :->
            val first = findViewById<TextInputEditText>(R.id.name_txt)
            val last = findViewById<TextInputEditText>(R.id.last_name_txt)
            val email = findViewById<TextInputEditText>(R.id.email)
            val phone = findViewById<TextInputEditText>(R.id.phone_txt)
            val pass = findViewById<TextInputEditText>(R.id.pass)
            val conPass = findViewById<TextInputEditText>(R.id.con_pass)

            // Edit Text Into String :->
            val firstStr = first.text.toString().trim()
            val lastStr = last.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val phoneStr = phone.text.toString().trim()
            val passStr = pass.text.toString().trim()
            val conPassStr = conPass.text.toString().trim()

            if (firstStr.isEmpty() || lastStr.isEmpty() || emailStr.isEmpty() || phoneStr.isEmpty() || passStr.isEmpty() || conPassStr.isEmpty()) {

                if (firstStr.isEmpty()) {
                    firstLayout.error = "Required*"
                    first.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    firstLayout.error = null
                    first.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                if (lastStr.isEmpty()) {
                    lastLayout.error = "Required*"
                    last.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    lastLayout.error = null
                    last.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                if (emailStr.isEmpty()) {
                    emailLayout.error = "Required*"
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    emailLayout.error = null
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                if (phoneStr.isEmpty()) {
                    phoneLayout.error = "Required*"
                    phone.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    phoneLayout.error = null
                    phone.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                if (passStr.isEmpty()) {
                    passLayout.error = "Required*"
                    pass.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    passLayout.error = null
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                if (conPassStr.isEmpty()) {
                    conPassLayout.error = "Required*"
                    conPass.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    conPassLayout.error = null
                    conPass.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                signupBtn.backgroundTintList = ResourcesCompat.getColorStateList(resources, R.color.light_grey, null)
                Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show()
            }else if (!emailStr.matches(emailPattern.toRegex())){
                emailLayout.error = "Enter valid email address"
                email.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            }else if (phoneStr.length<10){
                phoneLayout.error = "Number should be 10 in length"
                phone.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                Toast.makeText(this, "Enter Valid number", Toast.LENGTH_SHORT).show()
            }else if (passStr.length < 6){
                passLayout.error = "Enter more than 6 character"
                pass.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                Toast.makeText(this, "Enter Valid length", Toast.LENGTH_SHORT).show()
            }else if (passStr != conPassStr){
                conPassLayout.error = "Password not matched"
                conPass.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                Toast.makeText(this, "Enter Valid pass", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            val phoneNumber = countryCodePicker.selectedCountryCodeWithPlus + phoneStr
                            addUserToDatabase(firstStr, lastStr, emailStr, passStr, phoneNumber, auth.currentUser?.uid!!)
                            val dialog = Dialog(this@CustomerSignup)
                            dialog.setContentView(R.layout.dialog_loading)
                            dialog.show()
                        }else{
                            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Functioning Google Btn :->
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        google.setOnClickListener {
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
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
            Toast.makeText(this, "Home Screen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addUserToDatabase(firstStr: String, lastStr: String, emailStr: String, passStr: String, phoneNumber: String, uid: String) {
        val user = Users(firstStr, lastStr, emailStr, passStr, uid, phoneNumber)
        ref.child(firstStr).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Home Screen", Toast.LENGTH_SHORT).show()
        }
    }
}
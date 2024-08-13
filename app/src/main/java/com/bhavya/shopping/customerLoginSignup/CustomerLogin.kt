package com.bhavya.shopping.customerLoginSignup

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bhavya.shopping.R
import com.bhavya.shopping.onboarding.OnboardingHolder
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CustomerLogin : AppCompatActivity() {

    companion object{
        private const val Req_Code = 120
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var ref: DatabaseReference
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase Initializing :->
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("users")

        // Layout Initializing :->
        val emailLayout = findViewById<TextInputLayout>(R.id.email_layout)
        val passLayout = findViewById<TextInputLayout>(R.id.pass_layout)

        // Btn Initializing :->
        val loginBtn = findViewById<MaterialButton>(R.id.login_btn)
        val google = findViewById<MaterialButton>(R.id.google_btn)
        val back = findViewById<MaterialButton>(R.id.back_btn)

        // Text Initializing :->
        val headingTxt = findViewById<TextView>(R.id.txt)
        val account = findViewById<TextView>(R.id.text)
        val signupTxt = findViewById<TextView>(R.id.sign_up_txt)
        val forgot = findViewById<TextView>(R.id.forgot_password)
        val or = findViewById<LinearLayout>(R.id.or)

        // Back Btn Functioning :->
        back.setOnClickListener {
            startActivity(Intent(this@CustomerLogin, OnboardingHolder::class.java))
            finish()
        }

        // Functioning txt to the Login page :->
        signupTxt.setOnClickListener {
            startActivity(Intent(this@CustomerLogin, CustomerSignup::class.java))
            finish()
        }

        // Animation :->
        back.translationX = -300f
        headingTxt.translationX = -300f
        emailLayout.translationX = 300f
        passLayout.translationX = 300f
        forgot.translationX = 300f
        loginBtn.translationY = 300f
        or.translationY = 300f
        google.translationY = 300f
        account.translationX = -300f
        signupTxt.translationX = 300f

        back.alpha = 0f
        headingTxt.alpha = 0f
        emailLayout.alpha = 0f
        passLayout.alpha = 0f
        forgot.alpha = 0f
        loginBtn.alpha = 0f
        or.alpha = 0f
        google.alpha = 0f
        account.alpha = 0f
        signupTxt.alpha = 0f

        back.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(200).start()
        headingTxt.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(300).start()
        emailLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(500).start()
        passLayout.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(700).start()
        forgot.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(750).start()
        loginBtn.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        or.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(850).start()
        google.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(900).start()
        account.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(1000).start()
        signupTxt.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(1000).start()

        // Functioning Btn to get logged in :->
        loginBtn.setOnClickListener {

            // Layout Edit Text Initializing :->
            val email = findViewById<TextInputEditText>(R.id.email)
            val pass = findViewById<TextInputEditText>(R.id.pass)

            // Edit Text Into String :->
            val emailStr = email.text.toString().trim()
            val passStr = pass.text.toString().trim()

            if (emailStr.isEmpty() || passStr.isEmpty()){
                if (emailStr.isEmpty()){
                    emailLayout.error = "Required*"
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    emailLayout.error = null
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                if (passStr.isEmpty()){
                    passLayout.error = "Required*"
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
                }else {
                    passLayout.error = null
                    email.background = ResourcesCompat.getDrawable(resources, R.drawable.input_txt, null)
                }
                Toast.makeText(this, "Invalid Details!", Toast.LENGTH_SHORT).show()
            }else if (!emailStr.matches(emailPattern.toRegex())){
                emailLayout.error = "Enter valid email address"
                email.background = ResourcesCompat.getDrawable(resources, R.drawable.error_input_txt, null)
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

}
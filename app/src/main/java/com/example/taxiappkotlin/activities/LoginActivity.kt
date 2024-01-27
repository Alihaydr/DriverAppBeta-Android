package com.example.taxiappkotlin.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taxiappkotlin.R
import com.example.taxiappkotlin.databinding.ActivityLoginBinding
import com.example.taxiappkotlin.isConnected
import com.example.taxiappkotlin.longToastShow
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var progressDialog: ProgressDialog
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = applicationContext
        binding.txtCreate.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


        binding.loginButton.setOnClickListener {

            val email = binding.emailTxt.text.toString().trim()
            val password = binding.passTxt.text.toString().trim()

            if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Invalid email format
                Toast.makeText(applicationContext, "Invalid email address", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (password.isNotEmpty()) {
                    showProgressDialog()
                    loginUser(email, password)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter Email and Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



        }
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }
    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    private fun hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
    }

    private fun loginUser(email: String, password: String) {

        if (isConnected(this)){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(
                    email,
                    password)
                .addOnSuccessListener {
                    hideProgressDialog()
                    longToastShow("Login Successful")
                    startActivity(context, DriverActivity::class.java,true)
                    finish()
                }
                .addOnFailureListener {
                    it.message?.let { it1 -> longToastShow(it1) }
                }
        }else{
            longToastShow("No Internet Connection!")
        }



    }
    private fun startActivity(context: Context, to: Class<*>, setFinish: Boolean) {
        val intent = Intent(context, to)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (setFinish && context is AppCompatActivity) {
            context.finish()
        }
    }

}
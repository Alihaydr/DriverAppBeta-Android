package com.example.taxiappkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxiappkotlin.databinding.ActivitySignupBinding
import com.example.taxiappkotlin.isConnected
import com.example.taxiappkotlin.longToastShow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCreate.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener{

                if (isConnected(this)){

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(
                            binding.emailTxt.text.toString().trim(),
                            binding.passwordTxt.text.toString().trim())
                        .addOnSuccessListener {
                            val user = FirebaseAuth.getInstance().currentUser!!
                            val uid = user.uid

                            val database = FirebaseDatabase.getInstance()

                            val usersRef = database.getReference("drivers")

                            usersRef.child(uid).setValue("user.displayName")
                            val profileUpdate = UserProfileChangeRequest.Builder()
                                .setDisplayName(binding.usernameTxt.text.toString().trim())
                                .build()
                            user.updateProfile(profileUpdate).addOnSuccessListener {

                                longToastShow("Register Successful")
                                val mainIntent = Intent(this, DriverActivity::class.java)
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(mainIntent)
                                finish()
                            }.addOnFailureListener {
                                it.message?.let { it1 -> longToastShow(it1) }
                            }

                        }
                        .addOnFailureListener {
                            it.message?.let { it1 -> longToastShow(it1) }
                        }
                }else{
                    longToastShow("No Internet Connection!")
                }

        }
    }

}
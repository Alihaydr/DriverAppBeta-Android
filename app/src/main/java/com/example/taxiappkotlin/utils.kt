package com.example.taxiappkotlin

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging

fun Context.longToastShow(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}
fun Context.shortToastShow(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun validateConPassword(
    edPassword: TextInputEditText,
    edConPassword: TextInputEditText,
    edConPasswordL: TextInputLayout,
): Boolean {

    return when {
        edConPassword.text.toString().trim().isEmpty() -> {
            edConPasswordL.error = "Required"
            false
        }

        edConPassword.text.toString().trim().length < 8 || edConPassword.text.toString()
            .trim().length > 10 -> {
            edConPasswordL.error = "Password must be 8 to 10 Character!"
            false

        }

        edPassword.text.toString().trim() != edConPassword.text.toString().trim() -> {
            edConPasswordL.error = "Password Don't Match!"
            false
        }

        else -> {
            edConPasswordL.error = null
            true
        }
    }
}

fun validatePassword(
    edPassword: TextInputEditText,
    edPasswordL: TextInputLayout,
): Boolean {
    return when {
        edPassword.text.toString().trim().isEmpty() -> {
            edPasswordL.error = "Required"
            false
        }

        edPassword.text.toString().trim().length < 8 || edPassword.text.toString()
            .trim().length > 10 -> {
            edPasswordL.error = "Password must be 8 to 10 Character!"
            false
        }

        else -> {
            edPasswordL.error = null
            true
        }
    }
}

fun validateEmail(edEmail: TextInputEditText, edEmailL: TextInputLayout): Boolean {
    val emailPattern = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")
    return when {
        edEmail.text.toString().trim().isEmpty() -> {
            edEmailL.error = "Required"
            false
        }

        !edEmail.text.toString().trim().matches(emailPattern) -> {
            edEmailL.error = "Valid E-mail"
            false
        }

        else -> {
            edEmailL.error = null
            true
        }
    }
}

fun validateName(edName: EditText, edNameL: TextInputLayout): Boolean {
    return when {
        edName.text.toString().trim().isEmpty() -> {
            edNameL.error = "Required"
            false
        }

        else -> {
            edNameL.error = null
            true
        }
    }
}

fun isConnected(context: Context):Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return run {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val cap = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        when {
            cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}

object ActivityUtils {

    fun startActivity(context: Context, to: Class<*>, setFinish: Boolean) {
        val intent = Intent(context, to)
        context.startActivity(intent)
        if (setFinish && context is androidx.appcompat.app.AppCompatActivity) {
            context.finish()
        }
    }

}

object FirebaseUtils {


    fun getToken(context: Context):String{

        val token = ""
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            // Save the token to your server or associate it with the driver's account
            println("FCM Token: $token")
            Toast.makeText(context, "FCM Token: $token", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            println("Failed to get FCM token: $exception")
        }
        return token
    }
}
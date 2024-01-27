package com.example.taxiappkotlin.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.taxiappkotlin.ActivityUtils
import com.example.taxiappkotlin.FirebaseUtils
import com.example.taxiappkotlin.R
import com.example.taxiappkotlin.databinding.ActivityDriverBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class DriverActivity : AppCompatActivity() {
    lateinit var binding: ActivityDriverBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = applicationContext
        val drawerLayout: DrawerLayout = binding.myDrawerLayout
        val navView: NavigationView = binding.navigation
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        FirebaseUtils.getToken(context)
        val user = FirebaseAuth.getInstance().currentUser

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setCheckedItem(R.id.nav_orders)
        navView.setNavigationItemSelectedListener {
            // Handle navigation item clicks here
            when (it.itemId) {
                R.id.nav_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    // Handle item 1 click
                    false
                }
                R.id.nav_orders -> {
                    // Handle item 2 click
                    Toast.makeText(this, "My Orders", Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_settings -> {
                    // Handle item 3 click
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_driver -> {
                    // Handle item 4 click
                    FirebaseAuth.getInstance().signOut()
                    ActivityUtils.startActivity(context, LoginActivity::class.java, true)
                    Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show()
                    false
                }
                // Add more cases for additional items
                else -> false
            }
        }

//        val displayName = "name : "+user!!.displayName +"\nemail : "+ user.email+"\nphoneNumber : "+ user.phoneNumber
//
////        Toast.makeText(this, displayName, Toast.LENGTH_SHORT).show()
    }



}
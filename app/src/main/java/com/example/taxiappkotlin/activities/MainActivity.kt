package com.example.taxiappkotlin.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.taxiappkotlin.services.LocationService
import com.example.taxiappkotlin.R
import com.example.taxiappkotlin.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationService: LocationService
    private lateinit var currentLocation: Location
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var drawerLayout: DrawerLayout
    lateinit var  actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toggle: ActionBarDrawerToggle

    private var permissionCode = 101
    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = findViewById(R.id.myDrawer_layout)

//        val fragment = MapFragment()
//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment)
//        transaction.commit()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val navView: NavigationView = findViewById(R.id.navigation)
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
                    // Handle item 2 click
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_driver -> {
                    // Handle item 2 click
                    Toast.makeText(this, "Become a Driver", Toast.LENGTH_SHORT).show()
                    false
                }
                // Add more cases for additional items
                else -> false
            }
        }


        binding.fab.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }


        val user = FirebaseAuth.getInstance().currentUser
        val displayName = "name : "+user!!.displayName +"\nemail : "+ user.email+"\nphoneNumber : "+ user.phoneNumber

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // check conndition for drawer item with menu item
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }

    }
    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    // Update the map with the new location
                    updateMap(location)
                }
            }
        }
    }


    private fun getCurrentLocationUser() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                permissionCode
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Initialize the map with the current location
                updateMap(location)

                // Start receiving live location updates
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000 // 5 seconds
            fastestInterval = 3000 // 3 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun updateMap(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        Toast.makeText(this, "latitude : "+location.latitude+" longitude : "+location.longitude, Toast.LENGTH_SHORT).show()
        val markerOptions = MarkerOptions().position(latLng).title("Current Location").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))


        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        mMap.clear()
        mMap.addMarker(markerOptions)

//        GifMarker(this, mMap, "https://media.giphy.com/avatars/XXLREKLAM/8jPMxs6uUZDk.gif", latLng)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Apply the custom map style from the JSON resource file
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.custom_map_style
                )
            )

            if (!success) {
                // Handle map style load failure
            }
        } catch (e: Exception) {
            // Handle exception (e.g., invalid JSON format)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationUser()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop location updates when the activity is destroyed
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }



}
//package com.example.taxiappkotlin;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Looper;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.view.GravityCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.taxiappkotlin.databinding.FragmentMapBinding;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MapStyleOptions;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.maps.DirectionsApi;
//import com.google.maps.GeoApiContext;
//import com.google.maps.model.DirectionsResult;
//import com.google.maps.model.DirectionsRoute;
//import com.google.maps.model.TravelMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MapFragment extends Fragment implements OnMapReadyCallback {
//
//
//    public MapFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//    private GoogleMap googleMap;
//    private Spinner spinner;
//    LocationRequest mLocationRequest;
//    Location mLastLocation;
//    Marker mCurrLocationMarker;
//    FusedLocationProviderClient mFusedLocationClient;
//
//    LatLng startLocation = new LatLng(33.050643, 35.100000);
//    LatLng endLocation = new LatLng(34.691711, 36.623854);
//    Polyline routePolyline;
//    public FragmentMapBinding binding;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_map, container, false);;
//
//        try {
//
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
//
//            SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
//            FragmentTransaction fragmentTransaction =
//                    getChildFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.map_fragment, mMapFragment);
//            fragmentTransaction.commit();
//            mMapFragment.getMapAsync(this);
//
////            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
////            if (mapFragment != null) {
////                mapFragment.getMapAsync(this);
////            }
//
//        }catch (Exception e){
//
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.d("Mappp", e.getMessage());
//
//        }
//
//        return view;
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        //stop location updates when Activity is no longer active
//        if (mFusedLocationClient != null) {
//            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//        }
//    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        // Obtain a reference to the GoogleMap object
//        this.googleMap = googleMap;
//        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//        googleMap.getUiSettings().setCompassEnabled(false);
//
//        //  drawRoute();
//        try {
//            boolean success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            getContext(), R.raw.custom_map_style
//                    )
//            );
//
//            if (!success) {
//                // Handle map style load failure
//            }
//        } catch (Exception e) {
//            // Handle exception (e.g., invalid JSON format)
//        }
//        // Set the LatLngBounds for Lebanon
////        LatLngBounds lebanonBounds = new LatLngBounds(
////                new LatLng(33.050643, 35.100000), // Southwest corner (latitude, longitude)
////                new LatLng(34.691711, 36.623854)  // Northeast corner (latitude, longitude)
////        );
////
////
////        googleMap.setLatLngBoundsForCameraTarget(lebanonBounds);
////
////        // Set the initial camera position to focus on Lebanon
////        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(lebanonBounds, 0);
////        googleMap.moveCamera(cameraUpdate);
////
////        int xOffset = 50; // Adjust the value as needed
////        int yOffset = 0;   // Adjust the value as needed
////        googleMap.moveCamera(CameraUpdateFactory.scrollBy(xOffset, yOffset));
//
//        float maxZoomLevel = 8f;
//        googleMap.setMinZoomPreference(maxZoomLevel);
//
//
//
//        // Add markers to the map
////        addMarkers();
//
//
////        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(120000); // two minute interval
//        mLocationRequest.setFastestInterval(120000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(getContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                //Location Permission already granted
//                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//                googleMap.setMyLocationEnabled(true);
//            } else {
//                //Request Location Permission
//                checkLocationPermission();
//            }
//        }
//        else {
//            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//            googleMap.setMyLocationEnabled(true);
//        }
//    }
//
//
//
//
//
//
//    private void drawRoute() {
//        googleMap.addMarker(new MarkerOptions().position(startLocation));
//        googleMap.addMarker(new MarkerOptions().position(endLocation));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 12));
//
//        DirectionsTask directionsTask = new DirectionsTask();
//        directionsTask.execute();
//    }
//
//    private class DirectionsTask extends AsyncTask<Void, Void, DirectionsResult> {
//
//        @Override
//        protected DirectionsResult doInBackground(Void... voids) {
//            try {
//                GeoApiContext geoApiContext = new GeoApiContext.Builder()
//                        .apiKey("AIzaSyDrfwD037G7dw77ktXD9__bV1sj6yAKZ44")
//                        .build();
//
//                return DirectionsApi.newRequest(geoApiContext)
//                        .mode(TravelMode.DRIVING)
//                        .origin(new com.google.maps.model.LatLng(startLocation.latitude, startLocation.longitude))
//                        .destination(new com.google.maps.model.LatLng(endLocation.latitude, endLocation.longitude))
//                        .await();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(DirectionsResult directionsResult) {
//            if (directionsResult != null) {
//                if (routePolyline != null) {
//                    routePolyline.remove();
//                }
//
//
//
//
//                DirectionsRoute route = directionsResult.routes[0];
//                List<com.google.maps.model.LatLng> points = route.overviewPolyline.decodePath();
//
//                List<LatLng> convertedPoints = new ArrayList<>();
//                for (com.google.maps.model.LatLng point : points) {
//                    LatLng convertedPoint = new LatLng(point.lat, point.lng);
//                    convertedPoints.add(convertedPoint);
//                }
//
//               // List<LatLng> decodedPoints = PolyUtil.simplify(PolyUtil.decode(PolyUtil.encode(points)));
//
//                PolylineOptions polylineOptions = new PolylineOptions()
//                        .addAll(convertedPoints)
//                        .width(8)
//                        .color(getResources().getColor(R.color.button_background_color));
//
//                routePolyline = googleMap.addPolyline(polylineOptions);
//
//                double distanceInMeters = route.legs[0].distance.inMeters;
//                double distanceInKilometers = distanceInMeters / 1000.0;
//
//                // Use the distance value as needed
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            List<Location> locationList = locationResult.getLocations();
//            if (locationList.size() > 0) {
//                //The last location in the list is the newest
//                Location location = locationList.get(locationList.size() - 1);
//                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
//                mLastLocation = location;
//                if (mCurrLocationMarker != null) {
//                    mCurrLocationMarker.remove();
//                }
//
//                //Place current location marker
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Current Position");
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                mCurrLocationMarker = googleMap.addMarker(markerOptions);
//                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
//
//                //move map camera
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
//            }
//        }
//    };
//    private void addMarkers() {
//        if (googleMap != null) {
//            // Add your marker code here
//            LatLng markerLocation = new LatLng(33.825788478301654, 35.49310289435131); // Example location: San Francisco
//            googleMap.addMarker(new MarkerOptions()
//                    .position(markerLocation)
//                    .title("Marker Title")
//                    .snippet("Marker Snippet"));
//
//            // Move the camera to the marker location
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLocation));
//        }
//    }
//
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    private void checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                new AlertDialog.Builder(getContext())
//                        .setTitle("Location Permission Needed")
//                        .setMessage("This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(getActivity(),
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION );
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION );
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(getContext(),
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//                        googleMap.setMyLocationEnabled(true);
//                    }
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
//}
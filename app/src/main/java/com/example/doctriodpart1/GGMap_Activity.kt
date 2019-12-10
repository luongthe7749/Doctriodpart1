package com.example.doctriodpart1

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.android.gms.maps.model.Polyline
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.GpsStatus
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.io.DataOutputStream
import java.io.IOException


class GGMap_Activity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {


    //    private lateinit var fusedLocationClient: FusedLocationProviderClient
////    lateinit var mapFragment : SupportMapFragment
////    lateinit var googleMap: GoogleMap
////    lateinit var locationManager: LocationManager
    internal lateinit var mLocationRequest: LocationRequest
    internal lateinit var mGoogleApiClient: GoogleApiClient
    internal lateinit var result: PendingResult<LocationSettingsResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggmap_)

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).build()
        mGoogleApiClient.connect()



        var locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ){
//            startActivity(Intent(applicationContext,GGMap2_Activity::class.java))
            var intent : Intent = getIntent()
            var toado_lat : Double = intent.getDoubleExtra("toado_latne",1.0)
            var toado_long : Double = intent.getDoubleExtra("toado_longne",1.0)

            // lay toa do latlong cho vi tri lam viec cua bac si
            intent = Intent(applicationContext,GGMap2_Activity::class.java)
            intent.putExtra("toado_latneee",toado_lat)
            intent.putExtra("toado_longneee",toado_long)
            startActivity(intent)

//            Toast.makeText(this,"Toa do lat: "+toado_lat+" long :  "+toado_long,Toast.LENGTH_LONG).show()
            Log.d("anhthe123","latneeeee :  "+toado_lat+" longneeeee:  "+toado_long)

        }else{

        }
    }

    override fun onConnected(p0: Bundle?) {

        mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 30 * 1000
        mLocationRequest.fastestInterval = 5 * 1000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build())

        result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
            override fun onResult(result: LocationSettingsResult) {
                val status = result.getStatus()
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                when (status.getStatusCode()) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                this@GGMap_Activity,
                                REQUEST_LOCATION
                            )
                        } catch (e: SendIntentException) {
                            // Ignore the error.
                        }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }// All location settings are satisfied. The client can initialize location
                // requests here.
                //...
                // Location settings are not satisfied. However, we have no way to fix the
                // settings so we won't show the dialog.
                //...
            }
        })

    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult()", Integer.toString(resultCode))

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        when (requestCode) {
            REQUEST_LOCATION -> when (resultCode) {
                Activity.RESULT_OK -> {
                    // All required changes were successfully made
                    Toast.makeText(this@GGMap_Activity, "Location enabled by user!", Toast.LENGTH_LONG).show()

                    var intent : Intent = getIntent()
                    var toado_lat : Double = intent.getDoubleExtra("toado_latne",1.0)
                    var toado_long : Double = intent.getDoubleExtra("toado_longne",1.0)

                    // lay toa do latlong cho vi tri lam viec cua bac si
                    intent = Intent(applicationContext,GGMap2_Activity::class.java)
                    intent.putExtra("toado_latneee",toado_lat)
                    intent.putExtra("toado_longneee",toado_long)
                    startActivity(intent)
                }
                Activity.RESULT_CANCELED -> {
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(this@GGMap_Activity, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show()
                }
                else -> {
                }
            }
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }



    companion object {
        internal val REQUEST_LOCATION = 199
    }

    @SuppressLint("MissingPermission")
    private fun obtieneLocalizacion(){

        var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient.lastLocation.addOnSuccessListener(object : OnSuccessListener<Location> {
            override fun onSuccess(location: Location) {
                // GPS location can be null if GPS is switched off
                var currentLat = location.latitude
                var currentLong = location.longitude
                Log.d("anhthe1234","  "+currentLat+"   "+currentLong)

                val intent : Intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(
                        "http://maps.google.com/maps?" +
                                "saddr="+currentLat+","+currentLong+"&daddr="+21.001222+","+105.826179));
                intent.setClassName(
                    "com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity");
                startActivity(intent)
            }
        })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(@NonNull e: Exception) {
                    e.printStackTrace()
                }
            })
    }
}

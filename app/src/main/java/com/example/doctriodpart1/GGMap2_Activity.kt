package com.example.doctriodpart1

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.widget.Button
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_ggmap2_.*

class GGMap2_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggmap2_)

        val buttonGGMap : Button = findViewById(R.id.buttonGGMap) as Button

        var intent : Intent = getIntent()
        val toado_lat : Double = intent.getDoubleExtra("toado_latneee",1.0)
        val toado_long : Double = intent.getDoubleExtra("toado_longneee",1.0)
        button2Back.setOnClickListener {
            startActivity(Intent(applicationContext,NavigationActivity::class.java))
            finish()
        }
        buttonGGMap.setOnClickListener{
            GetDirection(toado_lat,toado_long)
            Log.d("anhthe123","lat :  "+toado_lat+" long:  "+toado_long)
        }
        Glide.with(applicationContext).load("https://enterpriseengineeringnetwork.org/images/google-maps-logo-png-6.png").into(imageViewmapp)
    }


    @SuppressLint("MissingPermission")
    fun GetDirection(toado_latt: Double, toado_longg: Double){
        var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient.lastLocation.addOnSuccessListener(object : OnSuccessListener<Location> {
            override fun onSuccess(location: Location) {
                // GPS location can be null if GPS is switched off
                var currentLat = location.latitude
                var currentLong = location.longitude
                Log.d("anhthe1234","  "+currentLat+"   "+currentLong)

                val intent : Intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "http://maps.google.com/maps?" +
                                "saddr="+currentLat+","+currentLong+"&daddr="+toado_latt+","+toado_longg));
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

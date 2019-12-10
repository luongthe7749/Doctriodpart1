package com.example.doctriodpart1

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ListView
import com.example.doctriodpart1.Objects.Favorite_Doctor
import com.example.doctriodpart1.Objects.Favorite_Doctor_Adapter
import com.example.doctriodpart1.Objects.Hospital
import com.example.doctriodpart1.Objects.Hospital_Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_favorite__doctor_.*

class Favorite_Doctor_Activity : AppCompatActivity() {
    lateinit var mDatabase : DatabaseReference
    private lateinit var auth: FirebaseAuth

    var Hinhfav : String? = ""
    var Tenfav : String? = ""
    var Khoafav : String? = ""
    var Vitrifav : String? = ""
    var KhoangCachfav : Double? = 1.1
    var Latfav : Double? = 1.1
    var Longfav : Double? = 1.1
    var Ratefav : Double? = 1.1
    var Uiddfav : String? = ""

    lateinit var mang : ArrayList<Favorite_Doctor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite__doctor_)

        backk_doctor_favoritee.setOnClickListener{
            startActivity(Intent(applicationContext,NavigationActivity::class.java))
            finish()
        }

        val ListViewFav : ListView = findViewById(R.id.ListView_DoctorFav)
        mang = ArrayList()

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite")

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    Hinhfav  = dataSnapshot1.child("a_hinhh").value as String
                    Tenfav  = dataSnapshot1.child("b_tenn").value as String
                    Khoafav = dataSnapshot1.child("c_khoaa").value as String
                    Vitrifav = dataSnapshot1.child("d_vitrii").value as String
                    KhoangCachfav = dataSnapshot1.child("e_khoangcachh").value as Double
                    Latfav = dataSnapshot1.child("f_latt").value as Double
                    Longfav = dataSnapshot1.child("g_longg").value as Double
                    Ratefav = dataSnapshot1.child("h_ratee").value as Double
                    Uiddfav = dataSnapshot1.child("i_uidd").value as String


                    mang.add(
                        Favorite_Doctor(Hinhfav.toString(),Tenfav.toString(),Khoafav.toString(),Vitrifav.toString(),
                            KhoangCachfav!!, Latfav!!,Longfav!!, Ratefav!!, Uiddfav.toString()
                        )
                    )

                    ListViewFav.adapter = Favorite_Doctor_Adapter(this@Favorite_Doctor_Activity,R.layout.dong_favorite_doctor,mang)
                }

                ListViewFav.setOnItemClickListener({ parent, view, position, id ->

                Log.d("anhthe1234",mang.get(position).uidd)
                })
            }

        })
    }
    fun onCall(){
        run {
            val intent : Intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:0385535400")
            val permissionCheck = ContextCompat.checkSelfPermission(this@Favorite_Doctor_Activity, Manifest.permission.CALL_PHONE)
            val request_code : Int = 123
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@Favorite_Doctor_Activity, arrayOf(Manifest.permission.CALL_PHONE),request_code
                )
            } else {
                this@Favorite_Doctor_Activity.startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {

            123 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onCall()
            } else {
                Log.d("TAG", "Call Permission Not Granted")
            }
            else -> {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

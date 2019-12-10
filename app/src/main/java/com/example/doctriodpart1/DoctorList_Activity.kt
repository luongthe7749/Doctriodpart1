package com.example.doctriodpart1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.doctriodpart1.Objects.Doctors
import com.example.doctriodpart1.Objects.DoctorsAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor_list_.*

class DoctorList_Activity : AppCompatActivity() {

    lateinit var mDatabase : DatabaseReference
    var Ten : String? = ""
    var Vitri : String? = ""
    var Khoa : String? = ""
    var UrlImage : String? = ""
    var KhoangCach : Double? = 1.1
    var Lat : Double? = 1.1
    var Long : Double? = 1.1
    var Rate : Double? = 1.1
    var Uidd : String? = ""

    private lateinit var blogAdapter: DoctorsAdapter
    lateinit var mang : ArrayList<Doctors>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_)
        //
        val RecycleView_Doctor : RecyclerView = findViewById(R.id.ListView_DoctorFav) as RecyclerView
        mang= ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    UrlImage  = dataSnapshot1.child("a").value as String
                    Ten  = dataSnapshot1.child("b").value as String
                    Khoa = dataSnapshot1.child("c").value as String
                    Vitri = dataSnapshot1.child("d").value as String
                    KhoangCach = dataSnapshot1.child("e").value as Double
                    Lat = dataSnapshot1.child("f").value as Double
                    Long = dataSnapshot1.child("g").value as Double
                    Rate = dataSnapshot1.child("h").value as Double
                    Uidd = dataSnapshot1.child("i").value as String
                    mang.add(
                        Doctors(UrlImage.toString(),Ten.toString(),Khoa.toString(),Vitri.toString(),
                            KhoangCach!!, Lat!!,Long!!,Rate!!,Uidd.toString())
                    )

                    RecycleView_Doctor.apply {
                        layoutManager = LinearLayoutManager(this@DoctorList_Activity)
                        blogAdapter = DoctorsAdapter(this@DoctorList_Activity,mang)
                        adapter = blogAdapter
                    }
                    blogAdapter.submitlist(mang)
                }
            }

        })

        backk_doctor_favoritee.setOnClickListener {
           startActivity(Intent(applicationContext,NavigationActivity::class.java))
//            val dialog : Dialog = Dialog(this)
//        dialog.setContentView(R.layout.dialog_doctor)
//        dialog.show()
        }
    }

    fun onCall(){
        run {
            val intent : Intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:0385535400")
            val permissionCheck = ContextCompat.checkSelfPermission(this@DoctorList_Activity, Manifest.permission.CALL_PHONE)
            val request_code : Int = 123
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@DoctorList_Activity, arrayOf(Manifest.permission.CALL_PHONE),request_code
                )
            } else {
                this@DoctorList_Activity.startActivity(intent)
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


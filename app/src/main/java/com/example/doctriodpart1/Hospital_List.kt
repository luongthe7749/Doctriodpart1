package com.example.doctriodpart1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.doctriodpart1.Objects.General_Medicine
import com.example.doctriodpart1.Objects.General_Medicine_Adapter
import com.example.doctriodpart1.Objects.Hospital
import com.example.doctriodpart1.Objects.Hospital_Adapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hospital__list.*

class Hospital_List : AppCompatActivity() {
    lateinit var mDatabase : DatabaseReference
    var Tenbv : String? = ""
    var Vitribv : String? = ""
    var UrlImagebv : String? = ""
    var KhoangCachbv : String? = ""
    var Latbv : Double? = 1.1
    var Longbv : Double? = 1.1

    lateinit var mang :ArrayList<Hospital>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital__list)

        val ListViewbv : ListView = findViewById(R.id.listView_hospital)
        mang = ArrayList()

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Hospital")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    UrlImagebv  = dataSnapshot1.child("a_image").value as String
                    Tenbv  = dataSnapshot1.child("b_name").value as String
                    Vitribv = dataSnapshot1.child("c_place").value as String
                    KhoangCachbv = dataSnapshot1.child("d").value as String
                    Latbv = dataSnapshot1.child("e_lat").value as Double
                    Longbv = dataSnapshot1.child("f_long").value as Double

                    mang.add(
                        Hospital(UrlImagebv.toString(),Tenbv.toString(),Vitribv.toString(),KhoangCachbv.toString(),
                            Latbv!!, Longbv!!
                        )
                    )

                    ListViewbv.adapter = Hospital_Adapter(applicationContext,R.layout.dong_hospital,mang)
                }

                ListViewbv.setOnItemClickListener({ parent, view, position, id ->
                    val intent = Intent(applicationContext,GGMap_Activity::class.java)
                    intent.putExtra("toado_latne",mang.get(position).ToaDo_Lat)
                    intent.putExtra("toado_longne",mang.get(position).ToaDo_Long)
                    startActivity(intent)
                })
            }

        })

        backk_hospital_list.setOnClickListener {
            onBackPressed()
            finish()
        }

    }
}

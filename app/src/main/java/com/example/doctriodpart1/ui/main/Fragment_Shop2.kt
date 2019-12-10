package com.example.doctriodpart1.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.doctriodpart1.Content_Shop_Activity
import com.example.doctriodpart1.Objects.General_Medicine
import com.example.doctriodpart1.Objects.General_Medicine_Adapter
import com.example.doctriodpart1.R
import com.google.firebase.database.*

class Fragment_Shop2 : Fragment() {
    lateinit var mDatabase : DatabaseReference
    var Title : String? = ""
    var Detail : String? = ""
    var UrlImage : String? = ""
    var values : String? = ""
    var Element : String? = ""
    var Object : String? = ""
    var Protect : String? = ""

    lateinit var mang2 :ArrayList<General_Medicine>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_shop2,container,false)

        val GridView_HealthCare : GridView = view.findViewById(R.id.gridView_HeathCare)

        mang2 = ArrayList()

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Shop").child("health_care")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    UrlImage  = dataSnapshot1.child("a").value as String
                    Title  = dataSnapshot1.child("b").value as String
                    values = dataSnapshot1.child("c").value as String
                    Detail = dataSnapshot1.child("d").value as String
                    Element = dataSnapshot1.child("e").value as String
                    Object = dataSnapshot1.child("f").value as String
                    Protect = dataSnapshot1.child("g").value as String

                    mang2.add(General_Medicine(UrlImage.toString(),Title.toString(),values.toString(),Detail.toString(),
                        Element.toString(),Object.toString(),Protect.toString()))


                    GridView_HealthCare.adapter = General_Medicine_Adapter(view.context,R.layout.dong_general_medicine,mang2)
                }

                GridView_HealthCare.setOnItemClickListener({ parent, view, position, id ->
                    val intent = Intent(view.context, Content_Shop_Activity::class.java)
                    intent.putExtra("hinhh",mang2.get(position).Hinh)
                    intent.putExtra("tenn",mang2.get(position).Ten)
                    intent.putExtra("giaa",mang2.get(position).Gia)
                    intent.putExtra("motaa",mang2.get(position).MoTa)
                    intent.putExtra("thanhphann",mang2.get(position).ThanhPhan)
                    intent.putExtra("doituongg",mang2.get(position).DoiTuong)
                    intent.putExtra("baoquann",mang2.get(position).BaoQuan)
                    startActivity(intent)
                })
            }

        })



        return view
    }
}
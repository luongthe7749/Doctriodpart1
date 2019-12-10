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

class Fragment_Shop3 : Fragment() {
    lateinit var mdatabase : DatabaseReference

    var Title : String? = ""
    var Detail : String? = ""
    var UrlImage : String? = ""
    var values : String? = ""
    var Element : String? = ""
    var Object : String? = ""
    var Protect : String? = ""

    lateinit var mang3 :ArrayList<General_Medicine>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_shop3,container,false)

        val GridView_Beauty : GridView = view.findViewById(R.id.gridView_Beauty)

        mang3 = ArrayList()

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Shop").child("beauty")
        mdatabase.addValueEventListener(object : ValueEventListener {
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

                    mang3.add(General_Medicine(UrlImage.toString(),Title.toString(),values.toString(),Detail.toString(),
                        Element.toString(),Object.toString(),Protect.toString()))


                    GridView_Beauty.adapter = General_Medicine_Adapter(view.context,R.layout.dong_general_medicine,mang3)
                }

                GridView_Beauty.setOnItemClickListener({ parent, view, position, id ->
                    val intent = Intent(view.context, Content_Shop_Activity::class.java)
                    intent.putExtra("hinhh",mang3.get(position).Hinh)
                    intent.putExtra("tenn",mang3.get(position).Ten)
                    intent.putExtra("giaa",mang3.get(position).Gia)
                    intent.putExtra("motaa",mang3.get(position).MoTa)
                    intent.putExtra("thanhphann",mang3.get(position).ThanhPhan)
                    intent.putExtra("doituongg",mang3.get(position).DoiTuong)
                    intent.putExtra("baoquann",mang3.get(position).BaoQuan)
                    startActivity(intent)
                })
            }

        })

        return view
    }
}
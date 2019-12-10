package com.example.doctriodpart1.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListView
import android.widget.ScrollView
import com.example.doctriodpart1.Content_News_Activity
import com.example.doctriodpart1.Content_Shop_Activity
import com.example.doctriodpart1.Objects.*
import com.example.doctriodpart1.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor__photos_.*
import kotlinx.android.synthetic.main.fragment_shop1.*

class Fragment_Shop1 : Fragment() {

    lateinit var mDatabase : DatabaseReference
    var Title : String? = ""
    var Detail : String? = ""
    var UrlImage : String? = ""
    var values : String? = ""
    var Element : String? = ""
    var Object : String? = ""
    var Protect : String? = ""

    lateinit var mang :ArrayList<General_Medicine>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_shop1,container,false)

        val GridView_GeneralMedicine : GridView = view.findViewById(R.id.gridView_GeneralMedicine)

         mang = ArrayList()

            mDatabase = FirebaseDatabase.getInstance().getReference().child("Shop").child("general")
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

                    mang.add(General_Medicine(UrlImage.toString(),Title.toString(),values.toString(),Detail.toString(),
                        Element.toString(),Object.toString(),Protect.toString()))


                    GridView_GeneralMedicine.adapter = General_Medicine_Adapter(view.context,R.layout.dong_general_medicine,mang)
                }

                GridView_GeneralMedicine.setOnItemClickListener({ parent, view, position, id ->
                    val intent = Intent(view.context,Content_Shop_Activity::class.java)
                    intent.putExtra("hinhh",mang.get(position).Hinh)
                    intent.putExtra("tenn",mang.get(position).Ten)
                    intent.putExtra("giaa",mang.get(position).Gia)
                    intent.putExtra("motaa",mang.get(position).MoTa)
                    intent.putExtra("thanhphann",mang.get(position).ThanhPhan)
                    intent.putExtra("doituongg",mang.get(position).DoiTuong)
                    intent.putExtra("baoquann",mang.get(position).BaoQuan)
                    startActivity(intent)
                })
            }

        })
        return view
    }

}
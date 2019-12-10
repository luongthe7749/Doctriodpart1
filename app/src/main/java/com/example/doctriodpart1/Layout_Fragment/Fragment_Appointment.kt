package com.example.doctriodpart1.Layout_Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctriodpart1.R
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Favorite_Doctor_Activity
import com.example.doctriodpart1.NavigationActivity
import com.example.doctriodpart1.Objects.Appointment
import com.example.doctriodpart1.Objects.Appointment_Adapter
import com.example.doctriodpart1.Objects.Favorite_Doctor
import com.example.doctriodpart1.Objects.Favorite_Doctor_Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text
import spencerstudios.com.bungeelib.Bungee
import java.util.*
import kotlin.collections.ArrayList


class Fragment_Appointment : Fragment() {
    lateinit var mDatabase3: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var HinhApm : String? = ""
    var TenApm : String? = ""
    var KhoaApm : String? = ""
    var VitriApm : String? = ""
    var NgayApm : String? = ""
    var GioApm : String? = ""
    lateinit var mang : ArrayList<Appointment>

    val randomm: String = UUID.randomUUID().toString()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_appointment,container,false)
        val ListView_appointment : ListView = view.findViewById(R.id.listView_appointment)
//        (activity as NavigationActivity).supportActionBar!!.hide()

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        mDatabase3 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Appointment")

        mang = ArrayList()
        mDatabase3.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    HinhApm  = dataSnapshot1.child("a_hinhh").value as String
                    TenApm  = dataSnapshot1.child("b_tenn").value as String
                    KhoaApm = dataSnapshot1.child("c_khoaa").value as String
                    VitriApm = dataSnapshot1.child("d_vitrii").value as String
                    NgayApm = dataSnapshot1.child("e_ngay").value as String
                    GioApm = dataSnapshot1.child("f_gio").value as String



                    mang.add(
                        Appointment(HinhApm.toString(),TenApm.toString(),KhoaApm.toString(),VitriApm.toString(),
                            NgayApm.toString(), GioApm.toString()
                        )
                    )

                    ListView_appointment.adapter = Appointment_Adapter(view.context,R.layout.dong_appointment,mang)
                }

                ListView_appointment.setOnItemClickListener(AdapterView.OnItemClickListener{parent, view, position, id ->

                    val dialog5 : Dialog = Dialog(view.context,R.style.DialogTheme)
                    dialog5.setContentView(R.layout.dialog_appointment_detail)
                    // anh xa cac thanh phan co trong dialog
                    val Backk_appointment_detail : ImageView = dialog5.findViewById(R.id.backk_appointment_detail)
                    val Image_appointment_detail : ImageView = dialog5.findViewById(R.id.image_appointment_detail)
                    val Tenbacsi_appointment_detail : TextView = dialog5.findViewById(R.id.tenbacsi_appointment_detail)
                    val Khoabacsi_appointment_detail : TextView = dialog5.findViewById(R.id.khoabacsi_appointment_detail)
                    val Vitribacsi_appointment_detail : TextView = dialog5.findViewById(R.id.vitribacsi_appointment_detail)
                    val Button_cancel_appointment : Button = dialog5.findViewById(R.id.button_cancel_appointment)
                    val Txt_Date_Appointment_detail : TextView = dialog5.findViewById(R.id.txt_Date_Appointment_detail)
                    val Txt_Time_Appointment_detail : TextView = dialog5.findViewById(R.id.txt_Time_Appointment_detail)

                    Backk_appointment_detail.setOnClickListener {
                        dialog5.dismiss()
                    }

                    Glide.with(context).load(mang.get(position).Hinh).into(Image_appointment_detail)
                    Tenbacsi_appointment_detail.setText(mang.get(position).Ten)
                    Khoabacsi_appointment_detail.setText(mang.get(position).Khoa)
                    Vitribacsi_appointment_detail.setText(mang.get(position).ViTri)
                    Txt_Date_Appointment_detail.setText(mang.get(position).Ngay)
                    Txt_Time_Appointment_detail.setText(mang.get(position).Gio)

                    Button_cancel_appointment.setOnClickListener {
                        val alertDialog = AlertDialog.Builder(view.context)
                        alertDialog.setTitle("Warning !!!")
                        alertDialog.setMessage("do u want to cancel this appointment from list ?")
                        alertDialog.setPositiveButton("Yes"){dialog, which ->

                            mDatabase3.child(mang.get(position).Ten).removeValue();
                            Toast.makeText(context,"Canceled",Toast.LENGTH_LONG).show()
                            mang.removeAt(position)
                            Appointment_Adapter(view.context,R.layout.dong_appointment,mang).notifyDataSetChanged()
                            dialog5.dismiss()
                            (activity as NavigationActivity).ChangeFrag(1)
                            Bungee.fade(context)
                        }
                        alertDialog.setNegativeButton("NO"){dialog, which ->

                        }
                        alertDialog.show()

                    }

                    dialog5.show()
                })
            }

        })
        return view
    }
}
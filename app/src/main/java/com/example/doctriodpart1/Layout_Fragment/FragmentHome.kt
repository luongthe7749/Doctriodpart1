package com.example.doctriodpart1.Layout_Fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.doctriodpart1.*

class FragmentHome : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_home,container,false)
        val Linear_FindDoctor : LinearLayout = view.findViewById(R.id.linear_FindDoctor) as LinearLayout
        val Linear_Appointment : LinearLayout = view.findViewById(R.id.linear_Appointment)
        val Linear_News : LinearLayout = view.findViewById(R.id.linear_News) as LinearLayout
        val Linear_Medicine_Shop : LinearLayout = view.findViewById(R.id.linear_Medicine_Shop) as LinearLayout
        val Linear_Hospital : LinearLayout = view.findViewById(R.id.linear_Hospital) as LinearLayout
        val Linear_emergency : LinearLayout = view.findViewById(R.id.emergency) as LinearLayout
        // set su kien ng dung click vao button Find Doctor
        Linear_FindDoctor.setOnClickListener{
        startActivity(Intent(view.context,DoctorList_Activity::class.java))
        }
        //
        Linear_Hospital.setOnClickListener {
            startActivity(Intent(view.context, Hospital_List::class.java))
        }
        // set su kien khi ng dung click vao button Appointment
        Linear_Appointment.setOnClickListener {
            (activity as NavigationActivity).ChangeFrag(1)
        }
        // click vao news
        Linear_News.setOnClickListener{
            startActivity(Intent(view.context, Tab_Layout_News_Activity::class.java))
        }
        // click vao medicine shop
        Linear_Medicine_Shop.setOnClickListener {
            startActivity(Intent(view.context, Tab_Layout_Shop_Activity::class.java))
        }
//
        Linear_emergency.setOnClickListener {
            val dialog : Dialog = Dialog(view.context,R.style.DialogTheme)
            dialog.setContentView(R.layout.dialog_emergency)
            dialog.show()
        }

        (activity as NavigationActivity).supportActionBar!!.show()
        return view
    }
}
package com.example.doctriodpart1.Objects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.doctriodpart1.R

class Appointment_Adapter(var context: Context, var layout : Int, var appointmentList : ArrayList<Appointment>):BaseAdapter() {

    override fun getCount(): Int {
        return appointmentList.size
    }
    override fun getItem(position: Int): Any {
        return appointmentList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    class ViewHolder(view: View){
        var Image_appointment_list : ImageView
        var Tenbacsi_appointment_list : TextView
        var Khoabacsi_appointment_list : TextView
        var Vitribacsi_appointment_list : TextView
        var Txt_ngay_appointment_list : TextView
        var Txt_gio_appointment_list : TextView
        init {
            Image_appointment_list = view.findViewById(R.id.image_appointment_list)
            Tenbacsi_appointment_list = view.findViewById(R.id.tenbacsi_appointment_list)
            Khoabacsi_appointment_list = view.findViewById(R.id.khoabacsi_appointment_list)
            Vitribacsi_appointment_list = view.findViewById(R.id.vitribacsi_appointment_list)
            Txt_ngay_appointment_list = view.findViewById(R.id.txt_ngay_appointment_list)
            Txt_gio_appointment_list = view.findViewById(R.id.txt_gio_appointment_list)
        }

    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var holder : ViewHolder
        if(convertView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view =  layoutInflater.inflate(layout,null)
            holder = ViewHolder(view)
            view.setTag(holder)
        }else{
            view = convertView
            holder = convertView.getTag() as ViewHolder
        }
        var appointment : Appointment = getItem(position) as Appointment
        Glide.with(context).load(appointment.Hinh).into(holder.Image_appointment_list)
        holder.Tenbacsi_appointment_list.setText(appointment.Ten)
        holder.Khoabacsi_appointment_list.setText(appointment.Khoa)
        holder.Vitribacsi_appointment_list.setText(appointment.ViTri)
        holder.Txt_ngay_appointment_list.setText(appointment.Ngay)
        holder.Txt_gio_appointment_list.setText(appointment.Gio)
        return view as View
    }
}
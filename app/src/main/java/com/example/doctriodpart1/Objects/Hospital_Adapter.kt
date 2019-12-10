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

class Hospital_Adapter (var context: Context, var layout : Int, var hospitalList : ArrayList<Hospital>):
    BaseAdapter() {

    override fun getCount(): Int {
        return hospitalList.size
    }
    override fun getItem(position: Int): Any {
        return hospitalList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    class ViewHolder(view: View){
        var Image_hospital_list : ImageView
        var Tenbv_hospital_list : TextView
        var Vitribv_hospital_list : TextView
        var KhoangCachbv_hospital_list : TextView

        init {
            Image_hospital_list = view.findViewById(R.id.image_hospital_list)
            Tenbv_hospital_list = view.findViewById(R.id.tenbv_list)
            Vitribv_hospital_list = view.findViewById(R.id.vitribv_list)
            KhoangCachbv_hospital_list = view.findViewById(R.id.khoangcach__favorite)
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
        var hospital : Hospital = getItem(position) as Hospital
        Glide.with(context).load(hospital.Hinh).into(holder.Image_hospital_list)
        holder.Tenbv_hospital_list.setText(hospital.Ten)
        holder.Vitribv_hospital_list.setText(hospital.ViTri)
        holder.KhoangCachbv_hospital_list.setText(hospital.KhoangCach)
        return view as View
    }
}
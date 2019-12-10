package com.example.doctriodpart1.Objects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.R
import kotlinx.android.synthetic.main.dong_doctor_photos.view.*

class Doctor_Photos_Adapter(var context: Context, var layout : Int, var photoList : ArrayList<Doctor_Photos>) : BaseAdapter() {

    override fun getCount(): Int {
        return photoList.size
    }
    override fun getItem(position: Int): Any {
        return photoList.get(position)
    }

    override fun getItemId(position: Int): Long {
//        Toast.makeText(context,"ban chon: "+photoList.get(position),Toast.LENGTH_LONG).show()
        return 0
    }

    class ViewHolder(view: View){
        var Img_photos_doctor : ImageView
        init {
            Img_photos_doctor =  view.findViewById(R.id.img_photos_doctor) as ImageView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var holder :ViewHolder
        if(convertView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view =  layoutInflater.inflate(layout,null)
            holder = ViewHolder(view)
            view.setTag(holder)
        }else{
            view = convertView
            holder = convertView.getTag() as ViewHolder
        }
        var photos : Doctor_Photos = getItem(position) as Doctor_Photos
         Glide.with(context).load(photos.Photos).into(holder.Img_photos_doctor)
        return view as View
    }
}
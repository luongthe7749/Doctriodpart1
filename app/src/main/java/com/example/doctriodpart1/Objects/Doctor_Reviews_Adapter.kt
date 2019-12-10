package com.example.doctriodpart1.Objects

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.DashBoardActivity
import com.example.doctriodpart1.R
import com.example.doctriodpart1.Review_Doctor_Activitty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_review__doctor__activitty.*

class Doctor_Reviews_Adapter(var context: Review_Doctor_Activitty, var layout : Int, var binhLuanList : ArrayList<Doctor_Reviews>) : BaseAdapter(){

    override fun getCount(): Int {
        return binhLuanList.size
    }

    override fun getItem(position: Int): Any {
        return binhLuanList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    class ViewHolder(view: View){
        var Image_doctor_review : ImageView
        var Txt_Ten_doctor_review : TextView
        var Txt_ngaythang_review : TextView
        var Txt_binhluan_review : TextView
        var Image_dots : ImageView
        init {
            Image_doctor_review = view.findViewById(R.id.image_doctor_review)
            Txt_Ten_doctor_review = view.findViewById(R.id.txt_Ten_doctor_review)
            Txt_ngaythang_review = view.findViewById(R.id.txt_ngaythang_review)
            Txt_binhluan_review = view.findViewById(R.id.txt_binhluan_review)
            Image_dots = view.findViewById(R.id.image_dotss)
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
        var review : Doctor_Reviews = getItem(position) as Doctor_Reviews
        Glide.with(context).load(review.Hinh).into(holder.Image_doctor_review)
        holder.Txt_Ten_doctor_review.setText(review.Ten)
        holder.Txt_ngaythang_review.setText(review.Ngay)
        holder.Txt_binhluan_review.setText(review.BinhLuan)


             var auth: FirebaseAuth
            auth = FirebaseAuth.getInstance()
            val uidne = auth.currentUser?.uid

            if(binhLuanList.get(position).uidUser == uidne){
                holder.Image_dots.setOnClickListener {
                    ChangeImage(it,review.Ten,binhLuanList.get(position).uidbss,binhLuanList.get(position).uidCmt,position,binhLuanList.get(position).uidUser)
                }
            }
            else{
                holder.Image_dots.visibility = View.INVISIBLE
            }

        return view as View

    }
    fun ChangeImage(view: View,ten : String,uidbs: String, uidCmt : String,position: Int,uidUser : String) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_comments)
        popupMenu.setOnMenuItemClickListener {
            when (it.getItemId()) {
                R.id.menu_edit_cmt -> {
                    Toast.makeText(context,"edit "+ten,Toast.LENGTH_LONG).show()
                }
                R.id.menu_delete_cmt -> {
                    var alertDialog1 : AlertDialog.Builder = AlertDialog.Builder(this.context)
                    alertDialog1.setTitle("Warning !!!")
                    alertDialog1.setMessage("do u want to Delete this comment ?")
                    alertDialog1.setPositiveButton("Yes"){dialog, which ->
                         var mDatabase : DatabaseReference
                    mDatabase = FirebaseDatabase.getInstance().getReference("Doctor").child(uidbs).child("Review")
                        mDatabase.child(uidCmt).removeValue();
                        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()
                        binhLuanList.removeAt(position)
                        binhLuanList.clear()
                        notifyDataSetChanged()
                    }
                    alertDialog1.setNegativeButton("NO"){dialog, which ->
                    }
                    alertDialog1.show()

                    Log.d("anhthee",uidUser)
                }
            }
            false
        }
        popupMenu.show()
    }
}
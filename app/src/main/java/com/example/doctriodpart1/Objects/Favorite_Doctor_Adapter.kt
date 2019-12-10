package com.example.doctriodpart1.Objects

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import spencerstudios.com.bungeelib.Bungee
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Favorite_Doctor_Adapter(var context: Favorite_Doctor_Activity, var layout : Int, var favoriteList : ArrayList<Favorite_Doctor>) : BaseAdapter() {

    lateinit var mDatabase: DatabaseReference
    private lateinit var auth: FirebaseAuth

    val randomm: String = UUID.randomUUID().toString()

    override fun getCount(): Int {
        return favoriteList.size
    }

    override fun getItem(position: Int): Any {
        return favoriteList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    class ViewHolder(view: View) {
        var Image_doctor_favorite: ImageView
        var Tenbacsi__favorite: TextView
        var Khoabacsi__favorite: TextView
        var Vitribacsi__favorite: TextView
        var Heart_favorite: CheckBox
        var Khoangcach_favorite: TextView
        var Danhgia_favorite: TextView

        init {
            Image_doctor_favorite = view.findViewById(R.id.image_doctor_favorite) as ImageView
            Tenbacsi__favorite = view.findViewById(R.id.tenbacsi__favorite) as TextView
            Khoabacsi__favorite = view.findViewById(R.id.khoabacsi__favorite) as TextView
            Vitribacsi__favorite = view.findViewById(R.id.vitribacsi__favorite) as TextView
            Khoangcach_favorite = view.findViewById(R.id.khoangcach__favorite) as TextView
            Danhgia_favorite = view.findViewById(R.id.danhgia__favorite) as TextView
            Heart_favorite = view.findViewById(R.id.heart_favorite) as CheckBox
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var holder: ViewHolder
        if (convertView == null) {
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(layout, null)
            holder = ViewHolder(view)
            view.setTag(holder)
        } else {
            view = convertView
            holder = convertView.getTag() as ViewHolder
        }
        var favorite: Favorite_Doctor = getItem(position) as Favorite_Doctor
        Glide.with(context).load(favorite.Hinh).into(holder.Image_doctor_favorite)
        holder.Tenbacsi__favorite.setText(favorite.Ten)
        holder.Khoabacsi__favorite.setText(favorite.Khoa)
        holder.Vitribacsi__favorite.setText(favorite.ViTri)
        holder.Khoangcach_favorite.setText(favorite.KhoangCach.toString())
        holder.Danhgia_favorite.setText(favorite.Rate.toString())

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        holder.Heart_favorite.setOnClickListener {
            if(holder.Heart_favorite.isChecked == false){
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle("Warning !!!")
                alertDialog.setMessage("do u want to delete this doctor from favorite list ?")
                alertDialog.setPositiveButton("Yes"){dialog, which ->

                    mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite")
                    mDatabase.child(favorite.uidd).removeValue();
                    Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()
                    favoriteList.removeAt(position)
                    favoriteList.clear()
                    notifyDataSetChanged()
                    context.startActivity(Intent(context,Favorite_Doctor_Activity::class.java))
                    Bungee.fade(context)
                }
                alertDialog.setNegativeButton("NO"){dialog, which ->
                    holder.Heart_favorite.isChecked = true
                }
                alertDialog.show()
            }
        }

        holder.Image_doctor_favorite.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val uid = auth.currentUser?.uid

            mDatabase =
                FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite").child(randomm)

            // goi dialog
            val dialog: Dialog = Dialog(context, R.style.DialogTheme)
            dialog.setContentView(R.layout.dialog_doctor)
            // khai bao va anh xa cac thanh phan co trong dialog
            val Linear_dialog2: LinearLayout = dialog.findViewById(R.id.linear_dialog2)
            val Call_dialog: ImageView = dialog.findViewById(R.id.call_dialog)
            val Back_Dalog: ImageView = dialog.findViewById(R.id.backk_doctor_dialog)
            val Image_doctor_dialog: ImageView = dialog.findViewById(R.id.image_doctor_dialog)
            val Txt_Ten_doctor_dialog: TextView = dialog.findViewById(R.id.txt_Ten_doctor_dialog)
            val Txt_khoa_bacsi_dialog: TextView = dialog.findViewById(R.id.txt_khoa_bacsi_dialog)
            val Img_photos_dialog: ImageView = dialog.findViewById(R.id.img_photos_dialog)
            val Linear_dialog3: LinearLayout = dialog.findViewById(R.id.linear_dialog3)
            val ImageView_Direct: ImageView = dialog.findViewById(R.id.imageView_Direct)
            val Button_book_appointment: Button = dialog.findViewById(R.id.button_book_appointment)
            val CheckBoxHeart_dialog: CheckBox = dialog.findViewById(R.id.checkBoxHeart_dialog)


            // su kien phim back
            Back_Dalog.setOnClickListener {
                dialog.dismiss()
            }
            // phim photos
            Img_photos_dialog.setOnClickListener {
                context.startActivity(Intent(context, Doctor_Photos_Activity::class.java))
            }
            // phim call
            Call_dialog.setOnClickListener {
                context.onCall()
            }
            // chuyen man hinh sang doctor info activity
            Linear_dialog2.setOnClickListener {
                val dialog2: Dialog = Dialog(context, R.style.DialogTheme)
                dialog2.setContentView(R.layout.dialog_doctor_info)
                val Backk_doctor_info: ImageView = dialog2.findViewById(R.id.backk_doctor_info)
                val Image_doctor_dialog: ImageView = dialog2.findViewById(R.id.image_doctor_dialog)
                val Txt_Ten_doctor_dialog: TextView = dialog2.findViewById(R.id.txt_Ten_doctor_dialog)
                val TextViewKhoa_info: TextView = dialog2.findViewById(R.id.textViewKhoa_info)
                val About: TextView = dialog2.findViewById(R.id.about)
                val Andresssss: TextView = dialog2.findViewById(R.id.andresssss)
                val Time: TextView = dialog2.findViewById(R.id.time)

                Backk_doctor_info.setOnClickListener {
                    dialog2.dismiss()
                }
                Glide.with(context).load(favoriteList.get(position).Hinh).into(Image_doctor_dialog)
                Txt_Ten_doctor_dialog.text = favoriteList.get(position).Ten
                TextViewKhoa_info.text = favoriteList.get(position).Khoa
                Andresssss.text = favoriteList.get(position).ViTri

                dialog2.show()
            }

            CheckBoxHeart_dialog.isChecked = true
            // su kien khi ng dung click <3
            CheckBoxHeart_dialog.setOnClickListener {
                if (CheckBoxHeart_dialog.isChecked == false) {

                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setTitle("Warning !!!")
                    alertDialog.setMessage("do u want to delete this doctor from favorite list ?")
                    alertDialog.setPositiveButton("Yes"){dialog, which ->

                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite")
                        mDatabase.child(favorite.uidd).removeValue();
                        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()
                        favoriteList.removeAt(position)
                        notifyDataSetChanged()
                        context.startActivity(Intent(context,Favorite_Doctor_Activity::class.java))
                        Bungee.fade(context)
                    }
                    alertDialog.setNegativeButton("NO"){dialog, which ->
                        CheckBoxHeart_dialog.isChecked = true
                    }
                    alertDialog.show()
                }
            }


            //chuyen man hinh sang gg map:
            ImageView_Direct.setOnClickListener {
                context.startActivity(Intent(context, GGMap_Activity::class.java))
                // lay toa do latlong cho vi tri lam viec cua bac si
                val intent = Intent(context, GGMap_Activity::class.java)
                intent.putExtra("toado_latne", favoriteList.get(position).ToaDo_Lat)
                intent.putExtra("toado_longne", favoriteList.get(position).ToaDo_Long)
                context.startActivity(intent)
            }
            // chuyen man hinh sang review doctor activity
            Linear_dialog3.setOnClickListener {
                context.startActivity(Intent(context, Review_Doctor_Activitty::class.java))
            }
            // chuyen man hinh sang book appointment
            Button_book_appointment.setOnClickListener {
                // goi dialog
                val dialog1: Dialog = Dialog(context, R.style.DialogTheme)
                dialog1.setContentView(R.layout.dialog_book_appointment_date)

                // anh xa cac thanh phan co trong dialog
                val Backk_book_appointment_dialog: ImageView =
                    dialog1.findViewById(R.id.backk_book_appointment_dialog)
                val Linear_dialog_date: LinearLayout = dialog1.findViewById(R.id.linear_dialog_date)
                val Linear_dialog_time: LinearLayout = dialog1.findViewById(R.id.linear_dialog_time)
                val Button_book_appointment_dialog: Button =
                    dialog1.findViewById(R.id.button_book_appointment_dialog)
                val Image_doctor_appointment: ImageView =
                    dialog1.findViewById(R.id.image_doctor_appointment)
                val Tenbacsi_appointment: TextView = dialog1.findViewById(R.id.tenbacsi_appointment)
                val Khoabacsi_appointment: TextView =
                    dialog1.findViewById(R.id.khoabacsi_appointment)
                val Vitribacsi_appointment: TextView =
                    dialog1.findViewById(R.id.vitribacsi_appointment)
                val Txt_Date_Appointment: TextView = dialog1.findViewById(R.id.txt_Date_Appointment)
                val Txt_Time_Appointment: TextView = dialog1.findViewById(R.id.txt_Time_Appointment)


                // truyen du lieu bac si vao dialog book appointment
                val doctor: Favorite_Doctor = favoriteList.get(position)
                Glide.with(context).load(doctor.Hinh).into(Image_doctor_appointment)
                Tenbacsi_appointment.setText(doctor.Ten)
                Khoabacsi_appointment.setText(doctor.Khoa)
                Vitribacsi_appointment.setText(doctor.ViTri)

                // set su kien phim back trong dialog book appointment
                Backk_book_appointment_dialog.setOnClickListener {
                    dialog1.dismiss()
                }
                // su kien ng dung click vao button chon ngay
                Linear_dialog_date.setOnClickListener {

                    // chon ngay
                    val calendar = Calendar.getInstance()
                    val nam = calendar.get(Calendar.YEAR)
                    val thang = calendar.get(Calendar.MONTH)
                    val ngay = calendar.get(Calendar.DATE)
                    val datePickerDialog = DatePickerDialog(
                        context,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            val Dinhdangngay = SimpleDateFormat("dd/MM/yyyy")
                            Txt_Date_Appointment.setText("Date: " + Dinhdangngay.format(calendar.time))
                            // hien textview len
                            Txt_Date_Appointment.visibility = View.VISIBLE
                        }, nam, thang, ngay
                    )
                    datePickerDialog.show()
                }


                // su kien khi ng dung click vao button chon gio
                Linear_dialog_time.setOnClickListener {
                    // chon gio
                    val calendar = Calendar.getInstance()
                    val gio = calendar.get(Calendar.HOUR_OF_DAY)
                    val phut = calendar.get(Calendar.MINUTE)
                    //        int giay = calendar.get(calendar.SECOND);
                    val timePickerDialog = TimePickerDialog(
                        context,
                        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            calendar.set(0, 0, 0, hourOfDay, minute)
                            val simpleDateFormat = SimpleDateFormat("HH:mm a")
                            Txt_Time_Appointment.setText("Time: " + simpleDateFormat.format(calendar.time))
                            // hien textview len
                            Txt_Time_Appointment.visibility = View.VISIBLE
                        }, gio, phut, true
                    )
                    timePickerDialog.show()
                }


                // su kien khi ng dung click vao button book appointment
                Button_book_appointment_dialog.setOnClickListener {
                    var ngayy: String = Txt_Date_Appointment.text.toString()
                    var gioo: String = Txt_Time_Appointment.text.toString()
                    if (ngayy.equals("") || gioo.equals("")) {
                        Toast.makeText(context, "Please choose day and time !!!", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(context, "complete", Toast.LENGTH_LONG).show()
                        context.startActivity(
                            Intent(
                                context,
                                DoctorList_Activity::class.java
                            )
                        )
                    }
                }
                // show dialog 1
                dialog1.show()
            }
            // truyen du lieu tu activity sang dialog
            val doctor: Favorite_Doctor = favoriteList.get(position)
            Glide.with(context).load(doctor.Hinh).into(Image_doctor_dialog)
            Txt_Ten_doctor_dialog.setText(doctor.Ten)
            Txt_khoa_bacsi_dialog.setText(doctor.Khoa)

            dialog.show()
        }
        return view as View
    }
}
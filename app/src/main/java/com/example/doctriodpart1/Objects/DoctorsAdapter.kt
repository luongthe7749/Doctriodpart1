package com.example.doctriodpart1.Objects

import android.app.Dialog
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctriodpart1.*
import kotlinx.android.synthetic.main.dong_doctor.view.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import spencerstudios.com.bungeelib.Bungee
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// khi goi class, khai bao context : Docotor_Activity de lay context cho dialog
class DoctorsAdapter(var context : DoctorList_Activity, var doctorsList: List<Doctors> = ArrayList() ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabase2: DatabaseReference
    lateinit var mDatabase3: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var namee : String? = ""
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.dong_doctor,p0,false),
            context
        )
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        when(p0){
            is ViewHolder ->{

                p0.bind(doctorsList.get(position))
                p0.itemView.setOnClickListener(View.OnClickListener {
//                    Toast.makeText(context,"Ban chon: "+doctorsList.get(position).ToaDo_Lat+"  "+doctorsList.get(position).ToaDo_Long,Toast.LENGTH_LONG).show()
                    val randomm : String = doctorsList.get(position).Ten
                    auth = FirebaseAuth.getInstance()
                    val uid = auth.currentUser?.uid

                    mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite").child(randomm)
                    mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite")
                    mDatabase3 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Appointment").child(randomm)
                    // goi dialog
                    val dialog : Dialog = Dialog(context,R.style.DialogTheme)
                    dialog.setContentView(R.layout.dialog_doctor)
                    // khai bao va anh xa cac thanh phan co trong dialog
                    val Linear_dialog2 : LinearLayout = dialog.findViewById(R.id.linear_dialog2)
                    val Call_dialog : ImageView = dialog.findViewById(R.id.call_dialog)
                    val Back_Dalog : ImageView = dialog.findViewById(R.id.backk_doctor_dialog)
                    val Image_doctor_dialog : ImageView = dialog.findViewById(R.id.image_doctor_dialog)
                    val Txt_Ten_doctor_dialog : TextView = dialog.findViewById(R.id.txt_Ten_doctor_dialog)
                    val Txt_khoa_bacsi_dialog : TextView = dialog.findViewById(R.id.txt_khoa_bacsi_dialog)
                    val Img_photos_dialog : ImageView = dialog.findViewById(R.id.img_photos_dialog)
                    val Linear_dialog3 : LinearLayout = dialog.findViewById(R.id.linear_dialog3)
                    val ImageView_Direct : ImageView = dialog.findViewById(R.id.imageView_Direct)
                    val Button_book_appointment : Button = dialog.findViewById(R.id.button_book_appointment)
                    val CheckBoxHeart_dialog : CheckBox = dialog.findViewById(R.id.checkBoxHeart_dialog)


                    mDatabase2.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(data: DatabaseError) {
                        }

                        override fun onDataChange(data: DataSnapshot) {
                            for (dataSnapshot1 in data.getChildren()) {
//
                                namee = dataSnapshot1.child("b_tenn").value as String


                                if(namee.toString() == randomm){
                                    CheckBoxHeart_dialog.isChecked = true
                                }
//                                else{
//                                    CheckBoxHeart_dialog.isChecked = false
//                                }
                                Log.d("aaa",namee.toString())
                            }
                        }
                    })
                    // su kien phim back
                    Back_Dalog.setOnClickListener {
                        dialog.dismiss()
                    }
                    // phim photos
                    Img_photos_dialog.setOnClickListener {
                        context.startActivity(Intent(context,Doctor_Photos_Activity::class.java))
                    }
                    // phim call
                    Call_dialog.setOnClickListener {
                    context.onCall()
                    }
                    // chuyen man hinh sang doctor info activity
                    Linear_dialog2.setOnClickListener{
                        val dialog2 : Dialog = Dialog(context,R.style.DialogTheme)
                        dialog2.setContentView(R.layout.dialog_doctor_info)
                        val Backk_doctor_info : ImageView = dialog2.findViewById(R.id.backk_doctor_info)
                        val Image_doctor_dialog : ImageView = dialog2.findViewById(R.id.image_doctor_dialog)
                        val Txt_Ten_doctor_dialog : TextView = dialog2.findViewById(R.id.txt_Ten_doctor_dialog)
                        val TextViewKhoa_info : TextView = dialog2.findViewById(R.id.textViewKhoa_info)
                        val About : TextView = dialog2.findViewById(R.id.about)
                        val Andresssss : TextView = dialog2.findViewById(R.id.andresssss)
                        val Time : TextView = dialog2.findViewById(R.id.time)

                        Backk_doctor_info.setOnClickListener{
                            dialog2.dismiss()
                        }
                        Glide.with(context).load(doctorsList.get(position).Hinh).into(Image_doctor_dialog)
                        Txt_Ten_doctor_dialog.text = doctorsList.get(position).Ten
                        TextViewKhoa_info.text = doctorsList.get(position).Khoa
                        Andresssss.text = doctorsList.get(position).ViTri

                        dialog2.show()
                    }

                    // su kien khi ng dung click <3

                    CheckBoxHeart_dialog.setOnClickListener {
                        if(CheckBoxHeart_dialog.isChecked){
                            AddToFavorite(doctorsList.get(position).Hinh,doctorsList.get(position).Ten, doctorsList.get(position).Khoa,doctorsList.get(position).ViTri,doctorsList.get(position).KhoangCach,doctorsList.get(position).ToaDo_Lat,doctorsList.get(position).ToaDo_Long,doctorsList.get(position).Rate,randomm)


                        }
                        if (CheckBoxHeart_dialog.isChecked == false) {
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.setTitle("Warning !!!")
                            alertDialog.setMessage("do u want to delete this doctor from favorite list ?")
                            alertDialog.setPositiveButton("Yes"){dialog, which ->

                                mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Favorite")
                                mDatabase.child(doctorsList.get(position).Ten).removeValue();
                                Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()
                            }
                            alertDialog.setNegativeButton("NO"){dialog, which ->
                                CheckBoxHeart_dialog.isChecked = true
                            }
                            alertDialog.show()
                    }
                    }


                    //chuyen man hinh sang gg map:
                    ImageView_Direct.setOnClickListener {
                        context.startActivity(Intent(context,GGMap_Activity::class.java))
                        // lay toa do latlong cho vi tri lam viec cua bac si
                        val intent = Intent(context,GGMap_Activity::class.java)
                        intent.putExtra("toado_latne",doctorsList.get(position).ToaDo_Lat)
                        intent.putExtra("toado_longne",doctorsList.get(position).ToaDo_Long)
                        context.startActivity(intent)
                    }
                    // chuyen man hinh sang review doctor activity
                    Linear_dialog3.setOnClickListener{
                      val intent =  Intent(context,Review_Doctor_Activitty::class.java)
                        intent.putExtra("hinhhh",doctorsList.get(position).Hinh)
                        intent.putExtra("tennn",doctorsList.get(position).Ten)
                        intent.putExtra("khoaaa",doctorsList.get(position).Khoa)
                        intent.putExtra("uiddd",doctorsList.get(position).Uid)
                        context.startActivity(intent)
                    }
                    // chuyen man hinh sang book appointment
                        Button_book_appointment.setOnClickListener {
                            // goi dialog
                            val dialog1 : Dialog = Dialog(context,R.style.DialogTheme)
                            dialog1.setContentView(R.layout.dialog_book_appointment_date)

                            // anh xa cac thanh phan co trong dialog
                            val Backk_book_appointment_dialog : ImageView = dialog1.findViewById(R.id.backk_book_appointment_dialog)
                            val Linear_dialog_date : LinearLayout = dialog1.findViewById(R.id.linear_dialog_date)
                            val Linear_dialog_time : LinearLayout = dialog1.findViewById(R.id.linear_dialog_time)
                            val Button_book_appointment_dialog : Button = dialog1.findViewById(R.id.button_book_appointment_dialog)
                            val Image_doctor_appointment : ImageView = dialog1.findViewById(R.id.image_doctor_appointment)
                            val Tenbacsi_appointment : TextView = dialog1.findViewById(R.id.tenbacsi_appointment)
                            val Khoabacsi_appointment : TextView = dialog1.findViewById(R.id.khoabacsi_appointment)
                            val Vitribacsi_appointment : TextView = dialog1.findViewById(R.id.vitribacsi_appointment)
                            val Txt_Date_Appointment : TextView= dialog1.findViewById(R.id.txt_Date_Appointment)
                            val Txt_Time_Appointment : TextView = dialog1.findViewById(R.id.txt_Time_Appointment)


                            // truyen du lieu bac si vao dialog book appointment
                            val doctor : Doctors = doctorsList.get(position)
                            Glide.with(context).load(doctor.Hinh).into(Image_doctor_appointment)
                            Tenbacsi_appointment.setText(doctor.Ten)
                            Khoabacsi_appointment.setText(doctor.Khoa)
                            Vitribacsi_appointment.setText(doctor.ViTri)

                            // set su kien phim back trong dialog book appointment
                                Backk_book_appointment_dialog.setOnClickListener {
                                    dialog1.dismiss()
                                }
                            // su kien ng dung click vao button chon ngay
                            Linear_dialog_date.setOnClickListener{

                                // chon ngay
                                val calendar = Calendar.getInstance()
                                val nam = calendar.get(Calendar.YEAR)
                                val thang = calendar.get(Calendar.MONTH)
                                val ngay = calendar.get(Calendar.DATE)
                                val datePickerDialog = DatePickerDialog(context,
                                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                        calendar.set(year, month, dayOfMonth)
                                        val Dinhdangngay = SimpleDateFormat("dd/MM/yyyy")
                                        Txt_Date_Appointment.setText("Date: "+Dinhdangngay.format(calendar.time))
                                        // hien textview len
                                        Txt_Date_Appointment.visibility = View.VISIBLE
                                    }, nam, thang, ngay
                                )
                                datePickerDialog.show()
                            }


                            // su kien khi ng dung click vao button chon gio
                            Linear_dialog_time.setOnClickListener{
                            // chon gio
                                val calendar = Calendar.getInstance()
                                val gio = calendar.get(Calendar.HOUR_OF_DAY)
                                val phut = calendar.get(Calendar.MINUTE)
                            //        int giay = calendar.get(calendar.SECOND);
                                val timePickerDialog = TimePickerDialog(context,
                                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                        calendar.set(0, 0, 0, hourOfDay, minute)
                                        val simpleDateFormat = SimpleDateFormat("HH:mm a")
                                        Txt_Time_Appointment.setText("Time: "+simpleDateFormat.format(calendar.time))
                                        // hien textview len
                                        Txt_Time_Appointment.visibility = View.VISIBLE
                                    }, gio, phut, true
                                )
                                timePickerDialog.show()
                            }


                            // su kien khi ng dung click vao button book appointment
                            Button_book_appointment_dialog.setOnClickListener {
                                var ngayy : String = Txt_Date_Appointment.text.toString()
                                var gioo : String = Txt_Time_Appointment.text.toString()
                                if(ngayy.equals("")|| gioo.equals("")){
                                    Toast.makeText(context,"Please choose day and time !!!",Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(context,"complete",Toast.LENGTH_LONG).show()
                                    AddToAppointmentList(doctor.Hinh,doctor.Ten,doctor.Khoa,doctor.ViTri,ngayy,gioo)
                                    context.startActivity(Intent(context,DoctorList_Activity::class.java))
                                }
                            }
                            // show dialog 1
                            dialog1.show()
                        }
                    // truyen du lieu tu activity sang dialog
                    val doctor : Doctors = doctorsList.get(position)
                    Glide.with(context).load(doctor.Hinh).into(Image_doctor_dialog)
                    Txt_Ten_doctor_dialog.setText(doctor.Ten)
                    Txt_khoa_bacsi_dialog.setText(doctor.Khoa)

                    dialog.show()
                })
            }
        }
    }
    override fun getItemCount(): Int {
        return doctorsList.size
    }
    fun submitlist(doctorpost: List<Doctors>){
        doctorsList = doctorpost
    }
    private fun AddToFavorite(hinhh: String, tenn: String, khoa: String, vitri: String, khoangcach: Double, lat: Double,long : Double,rate : Double,uidd : String) {
        //
        val userMap = HashMap<String, Any>()
        userMap["a_hinhh"] = hinhh
        userMap["b_tenn"] = tenn
        userMap["c_khoaa"] = khoa
        userMap["d_vitrii"] = vitri
        userMap["e_khoangcachh"] = khoangcach
        userMap["f_latt"] = lat
        userMap["g_longg"] = long
        userMap["h_ratee"] = rate
        userMap["i_uidd"] = uidd

        mDatabase.updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context,"Add to Favorite list SuccessFully",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Đã có lỗi sảy ra",Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun AddToAppointmentList(hinhh: String, tenn: String, khoa: String, vitri: String,ngay : String, gio : String) {
        //
        val userMap = HashMap<String, Any>()
        userMap["a_hinhh"] = hinhh
        userMap["b_tenn"] = tenn
        userMap["c_khoaa"] = khoa
        userMap["d_vitrii"] = vitri
        userMap["e_ngay"] = ngay
        userMap["f_gio"] = gio

        mDatabase3.updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context,"Add to Appointment list SuccessFully",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Đã có lỗi sảy ra",Toast.LENGTH_LONG).show()
            }
        }
    }

    class ViewHolder(itemView: View, var context: DoctorList_Activity): RecyclerView.ViewHolder(itemView){
        val hinh : ImageView = itemView.image_doctor_favorite
        val ten : TextView = itemView.tenbacsi__favorite
        val khoa : TextView = itemView.khoabacsi__favorite
        val vitri : TextView = itemView.vitribacsi__favorite
        val khoangcach : TextView = itemView.khoangcach__favorite
        val rate : TextView = itemView.danhgia__favorite

        fun bind(doctors: Doctors){
            Glide.with(context).load(doctors.Hinh).into(hinh)
            ten.text = doctors.Ten
            khoa.text = doctors.Khoa
            vitri.text = doctors.ViTri
            khoangcach.text = doctors.KhoangCach.toString()
            rate.text = doctors.Rate.toString()


        }
    }
}
package com.example.doctriodpart1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Objects.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor__photos_.*
import kotlinx.android.synthetic.main.activity_review__doctor__activitty.*
import spencerstudios.com.bungeelib.Bungee
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.app.Activity
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.inputmethod.InputMethodManager


class Review_Doctor_Activitty : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabase2: DatabaseReference
    lateinit var mDatabase3: DatabaseReference
    private lateinit var auth: FirebaseAuth



    var nameeuser : String? = ""
    var UrlImage : String? = ""

    lateinit var mang : ArrayList<Doctor_Reviews>

    var hinh : String? = ""
    var tennn : String? = ""
    var ngayyy : String? = ""
    var binhluannn : String? = ""
    var uid_cmtt : String? = ""
    var uid_userr : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review__doctor__activitty)


//        mang.clear()
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid


        val intent : Intent = getIntent()
        val hinhbs : String = intent.getStringExtra("hinhhh")
        val tenbs : String = intent.getStringExtra("tennn")
        val khoabs : String = intent.getStringExtra("khoaaa")
        val uidbs : String = intent.getStringExtra("uiddd")

        mDatabase = FirebaseDatabase.getInstance().getReference("Doctor").child(uidbs).child("Review")
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
        mDatabase3 = FirebaseDatabase.getInstance().getReference("Doctor").child(uidbs).child("Review")

        mang = ArrayList()
        mDatabase3.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    hinh  = dataSnapshot1.child("a_hinhh").value as String
                    tennn  = dataSnapshot1.child("b_tenn").value as String
                    ngayyy = dataSnapshot1.child("c_ngay").value as String
                    binhluannn = dataSnapshot1.child("d_binhluan").value as String
                    uid_cmtt = dataSnapshot1.child("e_uidd_cmt").value as String
                    uid_userr = dataSnapshot1.child("f_uidd_user").value as String
                    mang.add(
                        Doctor_Reviews(hinh.toString(),tennn.toString(),ngayyy.toString(),binhluannn.toString(),uidbs,uid_cmtt.toString(),
                            uid_userr.toString()
                        )
                    )

                    listView_Review.adapter = Doctor_Reviews_Adapter(this@Review_Doctor_Activitty,R.layout.dong_review,mang)
                }

//                listView_Review.setOnItemLongClickListener { parent, view, position, id ->
//
//                }

            }

        })

        mDatabase2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data: DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                // get du lieu tu fire base vao cac truong thong tin va kiem tra dieu kien neu no null :
                 nameeuser = data?.child("name")?.value?.toString()
                 UrlImage  = data?.child("images")?.value?.toString()

                if(nameeuser == null){
                    nameeuser = "user"
                }


                if(UrlImage == null){
//                    Toast.makeText(context,"avatar hasn't uploaded",Toast.LENGTH_LONG).show()
                    // get email user
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
//                        var namee = user.displayName
//                        val emaill = user.email
                        val photoUrll = user.photoUrl.toString()
                        UrlImage = photoUrll

                        // neu user moi tao tk va chua co anh thi se tra ve anh mac dinh user:
                        if(UrlImage == "null"){
                            Toast.makeText(applicationContext,"anh null roi",Toast.LENGTH_LONG).show()
                            UrlImage = "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/facebook_user.jpg?alt=media&token=79522ba3-8f84-4585-8b7f-0ea4fc305628"
                        }

                        // get info:
//                        txtTenProfile.setText(namee)
//                        txtEmailProfile.setText(emaill)
                        Glide.with(applicationContext).load(UrlImage).into(image_users_review)

//            Glide.with(this).load(photoUrl).into(imageView_Avt);

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid
                    }
                }else{
                    Glide.with(applicationContext).load(UrlImage).into(image_users_review)
                }
            }
        })

        Glide.with(applicationContext).load(hinhbs).into(image_doctor_review)
        txt_Ten_doctor_review.setText(tenbs)
        textView10.setText(khoabs)

        backk_doctor_review.setOnClickListener {
            onBackPressed()
        }


        val calendar = Calendar.getInstance()

        val Dinhdangthoigian = SimpleDateFormat("dd/MM/YY")
        val DinhdangGio = SimpleDateFormat("HH:mm:ss a")

        var gioo : String = DinhdangGio.format(calendar.time).toString()

        var thoigiann : String = Dinhdangthoigian.format(calendar.time).toString()

        image_send_review.setOnClickListener {
            var comments : String = edt_comment_review.text.toString()
            if(comments.isEmpty()){
                Toast.makeText(applicationContext,"please input your comment",Toast.LENGTH_LONG).show()
            }else{
                UploadToReview(UrlImage.toString(), nameeuser.toString(),thoigiann+" - "+gioo,comments,uid)
                mang.clear()
                edt_comment_review.setText("")
                val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(it.getWindowToken(), 0)
            }

        }

    }

    private fun UploadToReview(hinhh: String, tenn: String, ngay: String, binhluan: String,uid_user : String) {
        //
        val randomm : String = UUID.randomUUID().toString()
        val userMap = HashMap<String, Any>()
        userMap["a_hinhh"] = hinhh
        userMap["b_tenn"] = tenn
        userMap["c_ngay"] = ngay
        userMap["d_binhluan"] = binhluan
        userMap["e_uidd_cmt"] = randomm
        userMap["f_uidd_user"] = uid_user

        mDatabase.child(randomm).updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext,"bih luan thành công", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(applicationContext,"Đã có lỗi sảy ra", Toast.LENGTH_LONG).show()
            }
        }
    }
}

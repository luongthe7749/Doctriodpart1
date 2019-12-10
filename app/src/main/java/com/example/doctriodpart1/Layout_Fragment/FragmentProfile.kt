package com.example.doctriodpart1.Layout_Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.*
import com.example.doctriodpart1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_profile.*

class FragmentProfile : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    lateinit var mImageStorage : StorageReference
     var namee : String? = ""
     var agee : String? = ""
     var bloodd : String? = ""
     var genderr : String? = ""
     var heightt : String? = ""
     var weightt : String? = ""
    var UrlImage : String? = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_profile,container,false)

//        (activity as NavigationActivity).supportActionBar!!.hide()

                var txtTenProfile : TextView = view.findViewById(R.id.textViewUserName_Profile) as TextView
                var imgAvtProfile : ImageView = view.findViewById(R.id.imageViewAvt_Profile) as ImageView
                var txtEmailProfile : TextView = view.findViewById(R.id.textViewEmail_Profile) as TextView
                val imgEditProfile : ImageView = view.findViewById(R.id.imageViewEditProfile) as ImageView
                val Linear_Garelly_profile : LinearLayout = view.findViewById(R.id.linear_Garelly_profile) as LinearLayout
                val Txt_age_frag : TextView = view.findViewById(R.id.txt_age_frag) as TextView
                val Txt_blood_frag : TextView = view.findViewById(R.id.txt_blood_frag) as TextView
                val Txt_gender_frag : TextView = view.findViewById(R.id.txt_gender_frag) as TextView
                val Txt_height_frag : TextView = view.findViewById(R.id.txt_height_frag) as TextView
                val Txt_weight_frag : TextView = view.findViewById(R.id.txt_weight_frag) as TextView
                val Linear_Favorite : LinearLayout = view.findViewById(R.id.linearr2) as LinearLayout
                 val Linear_Appointment : LinearLayout = view.findViewById(R.id.linearr3) as LinearLayout

//                val  Txt_age_frag : TextView = view.findViewById(R.id.txt_age_frag) as TextView

//        (activity as NavigationActivity).GetData(txtTenProfile,imgAvtProfile)

//        (activity as NavigationActivity).ChangeAvt(imgAvtProfile,txtTenProfile)


        Linear_Garelly_profile.setOnClickListener{
            startActivity(Intent(context,Album_User_Activity::class.java))
        }
        Linear_Favorite.setOnClickListener {
            startActivity(Intent(view.context,Favorite_Doctor_Activity::class.java))
        }
        Linear_Appointment.setOnClickListener {
            (activity as NavigationActivity).ChangeFrag(1)
        }



        // get data users
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
        mImageStorage = FirebaseStorage.getInstance().reference
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }
            override fun onDataChange(data: DataSnapshot) {

                // khi up image hoac ten len ma anh bi crash :
                if (activity == null) {
                    return
                }
                // get du lieu tu fire base vao cac truong thong tin va kiem tra dieu kien neu no null :
                namee = data?.child("name")?.value?.toString()
                txtTenProfile.setText(namee)
                agee = data?.child("age")?.value?.toString()
                if(agee == null){
                    Txt_age_frag.setText("null")
                }else{
                    Txt_age_frag.setText(agee)
                }
                bloodd = data?.child("blood")?.value?.toString()
                if(bloodd == null){
                    Txt_blood_frag.setText("null")
                }else{
                    Txt_blood_frag.setText(bloodd)
                }
                genderr = data?.child("gender")?.value?.toString()
                if(genderr == null){
                    Txt_gender_frag.setText("null")
                }else{
                    Txt_gender_frag.setText(genderr)
                }
                heightt = data?.child("height")?.value?.toString()
                if(heightt == null){
                    Txt_height_frag.setText("null")
                }else{
                    Txt_height_frag.setText(heightt)
                }

                weightt = data?.child("weight")?.value?.toString()
                if(weightt == null){
                    Txt_weight_frag.setText("null")
                }else{
                    Txt_weight_frag.setText(weightt)
                }


                UrlImage  = data?.child("images")?.value?.toString()
//
                if(UrlImage == null){
//                    Toast.makeText(context,"avatar hasn't uploaded",Toast.LENGTH_LONG).show()
                    // get email user
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
//                        var namee = user.displayName
                        val emaill = user.email
                        val photoUrll = user.photoUrl.toString()
                        UrlImage = photoUrll

                        // neu user moi tao tk va chua co anh thi se tra ve anh mac dinh user:
                        if(UrlImage == "null"){
                            Toast.makeText(context,"anh null roi",Toast.LENGTH_LONG).show()
                            UrlImage = "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/facebook_user.jpg?alt=media&token=79522ba3-8f84-4585-8b7f-0ea4fc305628"
                        }

                        // get info:
                        txtTenProfile.setText(namee)
                        txtEmailProfile.setText(emaill)
                        Glide.with(context).load(UrlImage).into(imgAvtProfile)

//            Glide.with(this).load(photoUrl).into(imageView_Avt);

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid
                    }
                }else{
                    Glide.with(context).load(UrlImage).into(imgAvtProfile)
                }

//
//                Log.d("anhthe123","link:  "+link)
//                Toast.makeText(applicationContext,"link :  "+link.toString(),Toast.LENGTH_LONG).show()
//
//                if(UrlImage == null || UrlImage.isEmpty()){
//                    imageView_Avt.setImageResource(R.drawable.ic_photo_camera_black_24dp)
//                }else{
//                    Glide.with(applicationContext).load(UrlImage).into(imageView_Avt);
//                }
            }

        })

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val emaill = user.email
            txtEmailProfile.setText(emaill)
        }



        imgEditProfile.setOnClickListener{

            val intent = Intent(view.context, Edit_Profile_Activity::class.java)
            //
            intent.putExtra("diachi_hinh_ne",UrlImage)
            var ten : String = txtTenProfile.text.toString()
            var tuoi : String = txt_age_frag.text.toString()
            var mau : String = txt_blood_frag.text.toString()
            var gioitinh : String = txt_gender_frag.text.toString()
            var chieucao : String = txt_height_frag.text.toString()
            var cannang : String = txt_weight_frag.text.toString()
            intent.putExtra("tenne",ten)
            intent.putExtra("tuoine",tuoi)
            intent.putExtra("maune",mau)
            intent.putExtra("gioitinhne",gioitinh)
            intent.putExtra("chieucaone",chieucao)
            intent.putExtra("cannangne",cannang)


            startActivity(intent)
        }


        return view
    }
}
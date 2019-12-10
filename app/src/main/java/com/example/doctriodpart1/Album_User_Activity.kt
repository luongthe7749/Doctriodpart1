package com.example.doctriodpart1

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Objects.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_album__user_.*
import kotlinx.android.synthetic.main.activity_doctor__photos_.*
import kotlinx.android.synthetic.main.activity_doctor__photos_.backk_doctor_photos
import kotlinx.android.synthetic.main.activity_doctor__photos_.gridView_Doctor_Photo
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_review__doctor__activitty.*
import spencerstudios.com.bungeelib.Bungee
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

class Album_User_Activity : AppCompatActivity() {
    lateinit var mang : ArrayList<Profile>
    val request_code_folder = 1234
    lateinit var mDatabase : DatabaseReference
    lateinit var mDatabase2 : DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var mImageStorage : StorageReference
    var filePath : Uri? = null

    var hinh : String? = ""
    var uid_hinh : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album__user_)
        mang = ArrayList()

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("album_images")

        mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("album_images")
        mImageStorage = FirebaseStorage.getInstance().reference

        mDatabase2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    hinh  = dataSnapshot1.child("a_hinhh").value as String
                    uid_hinh = dataSnapshot1.child("b_uidd").value as String
                    mang.add(Profile(hinh.toString(),uid_hinh.toString()))

                    gridView_Doctor_Photo.adapter = Profile_Adapter(applicationContext,R.layout.dong_doctor_photos,mang)
                }

                if(mang.size == 0){
                    emptyy.visibility = View.VISIBLE
                }

                gridView_Doctor_Photo.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                    val dialog = Dialog(this@Album_User_Activity)
                    dialog.setContentView(R.layout.dialog_photo_doctor)
                    val Img_doctor_photo_dialog : ImageView = dialog.findViewById(R.id.img_doctor_photo_dialog) as ImageView
                    Glide.with(applicationContext).load(mang.get(position).Photos).into(Img_doctor_photo_dialog)
                    dialog.show()
                })

                gridView_Doctor_Photo.setOnItemLongClickListener({ parent, view, position, id ->
                    val alertDialog = AlertDialog.Builder(this@Album_User_Activity)
                    alertDialog.setTitle("Warning !!!")
                    alertDialog.setMessage("do u want to delete this image ?")
                    alertDialog.setPositiveButton("Yes"){dialog, which ->
                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("album_images")
                        mDatabase.child(mang.get(position).uid_hinh).removeValue();
                        mang.clear()
                        Profile_Adapter(applicationContext,R.layout.dong_doctor_photos,mang).notifyDataSetChanged()
                        startActivity(Intent(this@Album_User_Activity,Album_User_Activity::class.java))
                        Bungee.fade(this@Album_User_Activity)
                    }
                    alertDialog.setNegativeButton("NO"){dialog, which ->
                    }
                    alertDialog.show()
                    return@setOnItemLongClickListener true
                })
            }

        })


        add_doctor_photos.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*");
            startActivityForResult(intent, request_code_folder)
        }


        backk_doctor_photos.setOnClickListener {
            startActivity(Intent(applicationContext,NavigationActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == request_code_folder && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data
            try {
                uploadImage()

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        else{
            filePath = null
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // ham upload image
    private fun uploadImage() {

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            auth = FirebaseAuth.getInstance()
            val uid = auth.currentUser?.uid

            val ref = mImageStorage.child("images_album").child(UUID.randomUUID().toString()+".jpg")
            ref.putFile(filePath!!).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    ref.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(uri: Uri?) {
                            val dowload_url : String = uri.toString()
                            val randomm : String = UUID.randomUUID().toString()
                            val userMap = HashMap<String, Any>()
                            userMap["a_hinhh"] = dowload_url
                            userMap["b_uidd"] = randomm

                            mDatabase.child(randomm).updateChildren(userMap).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
//                                    Toast.makeText(applicationContext,"thành công", Toast.LENGTH_LONG).show()
                                    Log.d("anhthe123","oke")
                                }
                                else{
                                    Toast.makeText(applicationContext,"Đã có lỗi sảy ra", Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                    })
                    emptyy.visibility = View.INVISIBLE
                    Toast.makeText(baseContext, "Update Sucessfully !!!", Toast.LENGTH_LONG).show()
                    mang.clear()
                    Profile_Adapter(applicationContext,R.layout.dong_doctor_photos,mang).notifyDataSetChanged()
                    progressDialog.dismiss()

                }
            }

            ref.putFile(filePath!!).addOnProgressListener (OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                    .totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            })

        }
        else{
            onBackPressed()
            finish()
        }
    }
}

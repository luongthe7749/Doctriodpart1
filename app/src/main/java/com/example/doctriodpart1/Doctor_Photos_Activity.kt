package com.example.doctriodpart1

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Objects.Doctor_Photos
import com.example.doctriodpart1.Objects.Doctor_Photos_Adapter
import kotlinx.android.synthetic.main.activity_doctor__photos_.*

class Doctor_Photos_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor__photos_)
        //
        var mang : ArrayList<Doctor_Photos> = ArrayList()
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor001.jpg?alt=media&token=344006dd-d8e8-4acc-b6c4-257bb642f00a"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor003.jpg?alt=media&token=d98b6199-048d-4976-9e98-19f2aa0f90fc"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor004.jpg?alt=media&token=77179bea-556b-4249-b8cb-d5ec7dd0dcc7"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor005.jpg?alt=media&token=2afd1445-1329-441c-b533-c076212ed741"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor006.jpg?alt=media&token=195a8e57-1ab4-42c2-8b42-72c21952315e"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor007.jpg?alt=media&token=25eea6a7-68c5-47cf-be6d-0364304fe944"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor008.jpg?alt=media&token=4cada6c8-cefe-4708-a9b1-99e45852bb18"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor009.jpg?alt=media&token=37b54732-343f-4789-9316-dc2995a268ff"))
        mang.add(Doctor_Photos("https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/doctors%2Fdoctor_strage%2Fdoctor010.jpg?alt=media&token=05f9f879-d3d4-42db-9a00-69db0da28c35"))

        gridView_Doctor_Photo.adapter = Doctor_Photos_Adapter(this,R.layout.dong_doctor_photos,mang)

        gridView_Doctor_Photo.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
//            Toast.makeText(this, "U Choose " + mang.get(position), Toast.LENGTH_SHORT).show()
            val dialog : Dialog = Dialog(this)
           dialog.setContentView(R.layout.dialog_photo_doctor)
            val Img_doctor_photo_dialog : ImageView = dialog.findViewById(R.id.img_doctor_photo_dialog) as ImageView
            Glide.with(applicationContext).load(mang.get(position).Photos).into(Img_doctor_photo_dialog)
            dialog.show()
        })

        backk_doctor_photos.setOnClickListener {
            onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_add_photos,menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item!!.itemId){
//            R.id.menu_add_photos ->{
//                Toast.makeText(applicationContext,"okkkkkkk",Toast.LENGTH_LONG).show()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}

package com.example.doctriodpart1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_profile.*


import com.bumptech.glide.Glide
import android.support.v7.app.AlertDialog
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class Edit_Profile_Activity : AppCompatActivity() {

    val request_code_camera = 123
    val request_code_folder = 1234
    lateinit var mDatabase : DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var mImageStorage : StorageReference
     var filePath : Uri? = null

    var mau : String = ""
    var gioitinh : String = ""
    @SuppressLint("NewApi", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
        mImageStorage = FirebaseStorage.getInstance().reference



//        val bar : ActionBar? = supportActionBar
//        bar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.drawable.custom_button)))

        val intent = getIntent()
        var ten : String = intent.getStringExtra("tenne")
        var tuoi : String = intent.getStringExtra("tuoine")
//        mau = intent.getStringExtra("maune")
//        var gioitinh : String = intent.getStringExtra("gioitinhne")
        var chieucao : String = intent.getStringExtra("chieucaone")
        var cannang : String = intent.getStringExtra("cannangne")
        var url_image : String = intent.getStringExtra("diachi_hinh_ne")
//
        Glide.with(applicationContext).load(url_image).into(imageViewAvatar_Edit)
        edtName_Profile.setText(ten)
        txtAgeee.setText(tuoi)
//        spinner_Blood.setSelection(mau)
//        txtAgeee.setText(ten)
        txtHeight.setText(chieucao)
        txtWeight.setText(cannang)
//        NavigationActivity.getDataa(ImageViewAvatar_Edit,this)

        txtAgeee.setOnClickListener {
            Dialogg(1)
        }
        txtHeight.setOnClickListener {
            Dialogg(2)
        }
        txtWeight.setOnClickListener {
            Dialogg(3)
        }
        backk.setOnClickListener {
            onBackPressed()
        }



        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val mang: ArrayList<String> = ArrayList()
        mang.add("Male")
        mang.add("Female")
        mang.add("Others")
        val mang1: ArrayList<String> = ArrayList()
        mang1.add("A")
        mang1.add("B")
        mang1.add("AB")
        mang1.add("O")

        val spinner_gender = findViewById<Spinner>(R.id.spinner_Gender)
        val spinner_blood = findViewById<Spinner>(R.id.spinner_Blood)

        if (spinner_gender != null) {

            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mang)

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner_gender.adapter = arrayAdapter


            spinner_gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    gioitinh = mang[position]
//                    Toast.makeText(applicationContext,""+gioitinh,Toast.LENGTH_LONG).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }
        if (spinner_blood != null){
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mang1)

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner_blood.adapter = arrayAdapter
            spinner_blood.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    mau = mang1[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }

            }
        }


        // su kien khi ng dung click vao hinh camera de chon cach up anh len
        imageView_Camera_Edit.setOnClickListener {
            ChangeImage(it)
        }

        // su kien click khi ng dung click vao button change trong Main2
        btnChange_Edit.setOnClickListener {
            var EditName : String = edtName_Profile.text.toString()
            var EditAge : String = txtAgeee.text.toString()
            var EditHeight : String = txtHeight.text.toString()
            var EditWeight : String = txtWeight.text.toString()
            // goi ham update username
            updateUsername(EditName, EditAge,mau,gioitinh,EditHeight,EditWeight)
//

        }

    }




    fun ChangeImage(view: View) {
        val popupMenu: PopupMenu = PopupMenu(applicationContext, view)
        popupMenu.inflate(R.menu.menu_camera)
        popupMenu.setOnMenuItemClickListener {
            when (it.getItemId()) {
                R.id.menu_Chup -> {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.CAMERA), request_code_camera
                    )

                }
                R.id.menu_garelly -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setType("image/*");
                    startActivityForResult(intent, request_code_folder)
                }
            }
            false
        }
        popupMenu.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == request_code_camera && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) run {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, request_code_camera)
        } else {
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode == request_code_camera && resultCode == Activity.RESULT_OK && data != null) {
            val bitmap = data.extras!!.get("data") as Bitmap
            imageViewAvatar_Edit.setImageBitmap(bitmap)
            btnChange_Edit.setOnClickListener {

                var EditName : String = edtName_Profile.text.toString()
                var EditAge : String = txtAgeee.text.toString()
                var EditHeight : String = txtHeight.text.toString()
                var EditWeight : String = txtWeight.text.toString()
                // goi ham update username
                updateUsername(EditName, EditAge,mau,gioitinh,EditHeight,EditWeight)
                UploadImageAndSaveUri(bitmap)
            }
        }
        if (requestCode == request_code_folder && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data
            try {
                val inputStream = contentResolver.openInputStream(filePath!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageViewAvatar_Edit.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        else{
            filePath = null
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun UploadImageAndSaveUri(bitmap: Bitmap) {

        val progressDialog2 = ProgressDialog(this)
        progressDialog2.setTitle("Uploading...")
        progressDialog2.show()

        val baos = ByteArrayOutputStream()
        //
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        val ref = mImageStorage.child("images_avatar").child(uid+ ".jpg")
        //
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos)
        val image = baos.toByteArray()
        val upload = ref.putBytes(image)
        upload.addOnCompleteListener{uploadtask ->
            if (uploadtask.isSuccessful){
//                ref.downloadUrl.addOnCompleteListener{urlTask ->
//                    urlTask.result?.let {
//                        imageUri = it
//
////                        Toast.makeText(applicationContext,""+imageUri.toString(),Toast.LENGTH_LONG).show()
//                    }
//                }
                ref.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                    override fun onSuccess(uri: Uri?) {
                        val dowload_url : String = uri.toString()
                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
                        mDatabase.child("images").setValue(dowload_url)
                    }

                })
                Toast.makeText(baseContext, "Update Sucessfully !!!", Toast.LENGTH_LONG).show()
                finish()
            }

            else{
                uploadtask.exception?.let {
                    Toast.makeText(applicationContext,""+it.message!!,Toast.LENGTH_LONG).show()
                }
            }
        }
        upload.addOnProgressListener (OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                .totalByteCount
            progressDialog2.setMessage("Uploaded " + progress.toInt() + "%")
        })
    }

    // ham su li su kien change profile
    private fun updateUsername(username: String, age : String,blood :String, gender: String, height : String, weight : String ){
        //

        val userMap = HashMap<String,Any>()
        userMap["name"] = username
        userMap["age"] = age
        userMap["blood"] = blood
        userMap["gender"] = gender
        userMap["height"] = height
        userMap["weight"] = weight

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
        mDatabase.updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uploadImage()
            }
        }
    }


    // ham upload image
    private fun uploadImage() {

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

//            val ref = mImageStorage.child("images_avatar/" + UUID.randomUUID().toString())
            auth = FirebaseAuth.getInstance()
            val uid = auth.currentUser?.uid

            val ref = mImageStorage.child("images_avatar").child(uid+ ".jpg")
            ref.putFile(filePath!!).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    ref.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(uri: Uri?) {
                            val dowload_url : String = uri.toString()
                            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
                            mDatabase.child("images").setValue(dowload_url)
                        }

                    })
                    Toast.makeText(baseContext, "Update Sucessfully !!!", Toast.LENGTH_LONG).show()
                    finish()
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




    fun Dialogg(chon : Int){

        val mBuilder = AlertDialog.Builder(this)

        val dialog = layoutInflater.inflate(R.layout.number_picker_dialog,null)
        mBuilder.setView(dialog)
        val mAlertDialog = mBuilder.show()

//        mAlertDialog.setContentView(R.layout.number_picker_dialog)
//        mAlertDialog.setCanceledOnTouchOutside(false)
        val btnDialogCancel : Button= dialog.findViewById(R.id.btn_cancel_dialog) as Button
        val btnDialogOk : Button = dialog.findViewById(R.id.btn_ok_dialog) as Button
        val txtTitleDialog : TextView = dialog.findViewById(R.id.txtTitleDialog) as TextView
        val txtValuesDialg : TextView = dialog.findViewById(R.id.txt_values_dialog) as TextView

        btnDialogCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }



        if(chon == 1){
            txtTitleDialog.setText("Age")
            val numberPicker = dialog.findViewById(R.id.numberPicker) as NumberPicker
            numberPicker.maxValue = 100
            numberPicker.minValue = 1
            numberPicker.value = 1
            numberPicker.wrapSelectorWheel = false

            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                txtValuesDialg.setText("Age : $newVal"+" Years")
                Log.d("Theee",""+numberPicker.value)

            }

            btnDialogOk.setOnClickListener {
                Log.d("Theee","so neeeee : "+numberPicker.value)
                var pickedValues_Age : Int = numberPicker.value
                val txtTuoi : TextView = findViewById(R.id.txtAgeee)
                txtTuoi.setText(pickedValues_Age.toString()+"")
                mAlertDialog.dismiss()
            }
        }
        if (chon == 2){
            txtTitleDialog.setText("Height")
            val numberPicker = dialog.findViewById(R.id.numberPicker) as NumberPicker
            numberPicker.maxValue = 250
            numberPicker.minValue = 0
            numberPicker.value = 1
            numberPicker.wrapSelectorWheel = false

            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                txtValuesDialg.setText("Height : $newVal"+" cm")
                Log.d("Theee",""+numberPicker.value)

            }

            btnDialogOk.setOnClickListener {
                Log.d("Theee","so neeeee : "+numberPicker.value)
                var pickedValues_Height : Int = numberPicker.value
                val txtHeight : TextView = findViewById(R.id.txtHeight)
                txtHeight.setText(pickedValues_Height.toString()+" Cm")
                mAlertDialog.dismiss()
            }
        }

        if (chon == 3){
            txtTitleDialog.setText("Height")
            val numberPicker = dialog.findViewById(R.id.numberPicker) as NumberPicker
            numberPicker.maxValue = 250
            numberPicker.minValue = 0
            numberPicker.value = 1
            numberPicker.wrapSelectorWheel = false

            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                txtValuesDialg.setText("Weight : $newVal"+" kg")
                Log.d("Theee",""+numberPicker.value)

            }

            btnDialogOk.setOnClickListener {
                Log.d("Theee","so neeeee : "+numberPicker.value)
                var pickedValues_Weight : Int = numberPicker.value
                val txtWeight : TextView = findViewById(R.id.txtWeight)
                txtWeight.setText(pickedValues_Weight.toString()+" Kg")
                mAlertDialog.dismiss()
            }
        }
    }
}

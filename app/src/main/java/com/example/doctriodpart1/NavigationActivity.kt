package com.example.doctriodpart1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Layout_Fragment.*
import com.example.doctriodpart1.ui.main.Fragment_Cart
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.jar.Manifest

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
        var hinh : Unit? = null

    private lateinit var auth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    lateinit var mImageStorage : StorageReference
     var namee : String? = ""
    var UrlImage : String? = ""
    lateinit var btm_menubar : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)


        LoadFragment(frag = FragmentHome())

        var navigationView :NavigationView = findViewById(R.id.nav_view) as NavigationView
        val header: View = navigationView.getHeaderView(0)
        val img_Avatar : ImageView = header.findViewById(R.id.imageView_Avatar) as ImageView
        val imgCancel : ImageView = header.findViewById(R.id.imageView_Cancel) as ImageView
        val txtTenAvt : TextView = header.findViewById(R.id.textViewTenAvt)

        imgCancel.setOnClickListener{
            onBackPressed()
        }
        img_Avatar.setOnClickListener{
            //            Toast.makeText(applicationContext,"okkkkkk",Toast.LENGTH_LONG).show()
            LoadFragment(frag = FragmentProfile())
            drawer_layout.closeDrawer(GravityCompat.START)
        }

         btm_menubar = findViewById(R.id.bottom_menubar) as BottomNavigationView

        val intent : Intent  = getIntent()
        val tenn : String? = intent.getStringExtra("oki")

        if(tenn == "kio"){
            btm_menubar.selectedItemId = R.id.menu_Shop
            LoadFragment(frag = Fragment_Cart())
            toolbar.visibility = View.GONE
        }else{
//            Toast.makeText(applicationContext,"k lam gi",Toast.LENGTH_LONG).show()
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

                namee = data?.child("name")?.value?.toString()
                if(namee == null){
                    txtTenAvt.setText("UserName")
                }else{
                    txtTenAvt.setText(namee)
                }


                UrlImage  = data?.child("images")?.value?.toString()
//
                if(UrlImage == null){
//                    Toast.makeText(applicationContext,"error",Toast.LENGTH_LONG).show()
                    // get email user
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
//                        var namee = user.displayName
                        val emaill = user.email
                        val photoUrll = user.photoUrl.toString()
                        UrlImage = photoUrll

                        if(UrlImage == "null"){
                            Toast.makeText(applicationContext,"anh null roi",Toast.LENGTH_LONG).show()
                            UrlImage = "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/facebook_user.jpg?alt=media&token=79522ba3-8f84-4585-8b7f-0ea4fc305628"
                        }

                        // get info:
                        txtTenAvt.setText(namee)
//                        textViewEmail_Info.setText(emaill)
                        Glide.with(applicationContext).load(UrlImage).into(img_Avatar)

//            Glide.with(this).load(photoUrl).into(imageView_Avt);

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid
                    }
                }else{
                    Glide.with(applicationContext).load(UrlImage).into(img_Avatar)
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
//            textViewEmail_Info.setText(emaill)
        }



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


//      vao trang chu thi ban dau se hien ra fragment home


        // bat su kien click tu nav_header:

        nav_view.setNavigationItemSelectedListener(this)


//        val txtTenProfile : TextView = fragggg.view.findViewById()
//        val imgAvtProfile : ImageView = findViewById(R.id.imageViewAvt_Profile) as ImageView


//         ham su li su kien click khi ng dung click vao item trong bottom menu bar
        btm_menubar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_Home -> {
                    LoadFragment1(frag = FragmentHome())
                    toolbar.visibility = View.GONE
                }
                R.id.menu_Appointment ->{
                    LoadFragment1(frag = Fragment_Appointment())
                    toolbar.visibility = View.GONE
                }
                R.id.menu_Profile ->{
                    val fragg : FragmentProfile = FragmentProfile()
                    val fm = supportFragmentManager.beginTransaction()
                    fm.replace(R.id.frame_layoutt, fragg)
                    fm.commit()
                    toolbar.visibility = View.GONE
                }
                R.id.menu_Shop ->{
                    val fm = supportFragmentManager.beginTransaction()
                    fm.replace(R.id.frame_layoutt, Fragment_Cart())
                    fm.commit()
                }
            }

            return@OnNavigationItemSelectedListener true
        })
    }

    // ham xu li su kien phim back
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // bat su kien ng dung click vao tung item trong menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menu_home -> {
                LoadFragment(frag = FragmentHome())
            }
            R.id.menu_drug -> {
//                LoadFragment(frag = Fragment2())
                startActivity(Intent(applicationContext,Album_User_Activity::class.java))
            }
            R.id.menu_service -> {
                startActivity(Intent(applicationContext,Favorite_Doctor_Activity::class.java))
            }
            R.id.menu_dashboard -> {
                ChangeFrag(2)
            }
            R.id.menu_profile -> {
                ChangeBtm(R.id.menu_Profile)
                toolbar.visibility = View.GONE
                val fragg = FragmentProfile()
                val fm = supportFragmentManager.beginTransaction()
                fm.replace(R.id.frame_layoutt, fragg)
                fm.commit()
//                applicationContext.setTheme(R.style.MyTheme)
            }
            R.id.menu_logout -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Warning !!!")
                alertDialog.setMessage("do u want to logout ?")
                alertDialog.setPositiveButton("Yes"){dialog, which ->
                    //                LoginManager.getInstance().logOut()
//                finish()
//                startActivity(Intent(applicationContext,MainActivity::class.java))
//                    signOut()
//
//                    disconnectFromFacebook()
                    FirebaseAuth.getInstance().signOut()
                    finish()
                    startActivity(Intent(applicationContext,DashBoardActivity::class.java))
                Toast.makeText(applicationContext,"Logged out",Toast.LENGTH_LONG).show()

                }
                alertDialog.setNegativeButton("NO"){dialog, which ->
                }
                alertDialog.show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    // chuyen man hinh khi ng dung click vao items trong menu bang fragment

     fun LoadFragment(frag : Fragment){
         toolbar.visibility = View.VISIBLE
        val fm = supportFragmentManager.beginTransaction()
         fm.setCustomAnimations(R.anim.split_enter,R.anim.split_exit)
        fm.replace(R.id.frame_layoutt, frag)
        fm.commit()
    }
    fun ChangeBtm(idd : Int){
        btm_menubar.selectedItemId = idd
    }
    fun LoadFragment1(frag : Fragment){
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.frame_layoutt, frag)
        fm.commit()
    }
    fun ChangeFrag(position : Int){
        when(position){
            1->{
             btm_menubar.selectedItemId = R.id.menu_Appointment
            }
            2->{
                val fm = supportFragmentManager.beginTransaction()
                fm.setCustomAnimations(R.anim.fade_enter,R.anim.fade_exit)
                fm.replace(R.id.frame_layoutt, Fragment_Cart())
                fm.commit()
                btm_menubar.selectedItemId = R.id.menu_Shop
            }
            3 -> {
                btm_menubar.selectedItemId = R.id.menu_Profile
            }
        }
    }
}

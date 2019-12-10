package com.example.doctriodpart1

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_content__shop_.*
import kotlinx.android.synthetic.main.activity_content__shop_.cart_medicine_shop
import kotlinx.android.synthetic.main.activity_tab__layout__shop_.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.HashMap

class Content_Shop_Activity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private lateinit var auth: FirebaseAuth

    val randomm : String = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content__shop_)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Cart").child(randomm)

        val intent: Intent = getIntent()

        val hinhh: String = intent.getStringExtra("hinhh")
        val tenn: String = intent.getStringExtra("tenn")
        val giaa: String = intent.getStringExtra("giaa")
        val motaa: String = intent.getStringExtra("motaa")
        val thanhphann: String = intent.getStringExtra("thanhphann")
        val doituongg: String = intent.getStringExtra("doituongg")
        val baoquann: String = intent.getStringExtra("baoquann")

        Glide.with(applicationContext).load(hinhh).into(imageView_Hinh_Detail)
        textView_Title.setText(tenn)
//        textView_Values.setText(giaa)
        textView_Detail.setText(motaa)
        textView_Element.setText(thanhphann)
        textView_Object.setText(doituongg)
        textView_Protect.setText(baoquann)

        val currentt: Long = giaa.toLong()
        val currenttt: String = fmNumber(currentt)
        textView_Values.setText(currenttt)

        val oldval: Long = (5 * currentt) / 4
        val oldvall: String = fmNumber(oldval)
//
////
        textView_Old_Values.setText(oldvall)
        textView_Old_Values.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //
        rela_AddtoCard.setOnClickListener {
            UploadToCart(hinhh,tenn,giaa,motaa,thanhphann,doituongg,baoquann,randomm,"1",giaa)
        }

        back_content_shop.setOnClickListener {
            onBackPressed()
            finish()
        }
        cart_medicine_shop.setOnClickListener {
            val intent = Intent(applicationContext,NavigationActivity::class.java)
            intent.putExtra("oki","kio")
            startActivity(intent)
        }

    }

    fun fmNumber(num: Long?): String {
        if (num == null) {
            return "0"
        }
        var bd = BigDecimal(num!!)
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
        val symbols = DecimalFormat().getDecimalFormatSymbols()
        symbols.setGroupingSeparator('.')
        var ret = DecimalFormat().format(bd) + ""
        return ret
    }

    private fun UploadToCart(hinhh: String, tenn: String, giaa: String, motaa: String, thanhphann: String, doituongg: String,baoquann : String,uidd : String, soluongg : String, tongg: String) {
        //

        val userMap = HashMap<String, Any>()
        userMap["a_hinhh"] = hinhh
        userMap["b_tenn"] = tenn
        userMap["c_giaa"] = giaa
        userMap["d_motaa"] = motaa
        userMap["e_thanhphann"] = thanhphann
        userMap["f_doituongg"] = doituongg
        userMap["g_baoquann"] = baoquann
        userMap["h_uid"] = uidd
        userMap["i_soluong"] = soluongg
        userMap["j_tong"] = tongg

        mDatabase.updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext,"Thêm vào giỏ hàng thành công",Toast.LENGTH_LONG).show()
//                val intent = Intent(applicationContext,NavigationActivity::class.java)
//                intent.putExtra("okeeeebanoi",2)
//                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Đã có lỗi sảy ra",Toast.LENGTH_LONG).show()
            }
        }
    }



}

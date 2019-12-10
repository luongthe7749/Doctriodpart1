package com.example.doctriodpart1.Objects

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Content_Shop_Activity
import com.example.doctriodpart1.NavigationActivity
import com.example.doctriodpart1.R
import com.example.doctriodpart1.ui.main.Fragment_Cart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import spencerstudios.com.bungeelib.Bungee
import java.math.BigDecimal
import java.text.DecimalFormat

class Cart_Adapter(var context: Context, var layout : Int, var cart_list : ArrayList<Cart>) : BaseAdapter() {
    lateinit var mDatabase : DatabaseReference
    lateinit var mDatabase2 : DatabaseReference
    private lateinit var auth: FirebaseAuth
    var TotalAllne : Long = 0

    override fun getItem(position: Int): Any {
        return cart_list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return cart_list.size
    }

    class ViewHolder(view: View){
        var ImageView_HinhCart : ImageView
        var TextView_TenCart : TextView
        var TextView_ValueCart : TextView
        var Rela_remove_cart : RelativeLayout
        var TextView_Number_Cart : TextView
        var TextView_total_cart : TextView
        var ImageView_add : ImageView
        var ImageView_minus : ImageView
        init {
            ImageView_HinhCart =  view.findViewById(R.id.imageView_HinhCart) as ImageView
            TextView_TenCart = view.findViewById(R.id.textView_Ten_Cart) as TextView
            TextView_ValueCart = view.findViewById(R.id.textView_values_cart_) as TextView
            Rela_remove_cart  = view.findViewById(R.id.rela_remove_cart) as RelativeLayout
            TextView_Number_Cart = view.findViewById(R.id.textView_Number_Cart)
            TextView_total_cart = view.findViewById(R.id.textView_total_cart)
            ImageView_add = view.findViewById(R.id.imageView_add) as ImageView
            ImageView_minus = view.findViewById(R.id.imageView_minus) as ImageView
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
        var cart : Cart = getItem(position) as Cart
        Glide.with(context).load(cart.Hinh).into(holder.ImageView_HinhCart)
        holder.TextView_TenCart.setText(cart.Ten)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        holder.Rela_remove_cart.setOnClickListener {

            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Chú ý !!")
            alertDialog.setMessage("Bạn Có muốn xóa vật phẩm này ?")
            alertDialog.setPositiveButton("Có"){dialog, which ->
                val intent = Intent(context,NavigationActivity::class.java)
                intent.putExtra("oki","kio")
                context.startActivity(intent)
                Bungee.fade(context)

                mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Cart")
                mDatabase.child(cart.uidd).removeValue();
                Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()
                cart_list.removeAt(position)
                cart_list.clear()
                notifyDataSetChanged()
            }
            alertDialog.setNegativeButton("Không"){dialog, which ->

            }
            alertDialog.show()

        }
        holder.ImageView_HinhCart.setOnClickListener {
            putData(position)
        }
        holder.TextView_TenCart.setOnClickListener{
            putData(position)
        }

        val giaa : Long = cart.Gia.toLong()
        val giaaa : String = fmNumber(giaa)
        holder.TextView_ValueCart.setText(giaaa)

        var soluongg : Int = cart.soluong.toInt()
        var tongg : Long = cart.Tong.toLong()
        holder.TextView_Number_Cart.setText(soluongg.toString())
        val tonggg : String = fmNumber(tongg)
        holder.TextView_total_cart.setText(tonggg)

        if(soluongg < 1|| soluongg == 1){
            holder.ImageView_minus.visibility = View.INVISIBLE
        }


        holder.ImageView_add.setOnClickListener {
            mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Cart").child(cart.uidd)
            holder.ImageView_minus.visibility = View.VISIBLE
            soluongg = soluongg + 1
            tongg = tongg + giaa
            mDatabase2.child("i_soluong").setValue(soluongg.toString())
            mDatabase2.child("j_tong").setValue(tongg.toString())

            holder.TextView_Number_Cart.setText(soluongg.toString())

            var activity : Activity = context as Activity
            (activity as NavigationActivity).ChangeFrag(2)
        }


        holder.ImageView_minus.setOnClickListener {

            mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Cart").child(cart.uidd)
            soluongg = soluongg - 1
            tongg = tongg - giaa
            mDatabase2.child("i_soluong").setValue(soluongg.toString())
            mDatabase2.child("j_tong").setValue(tongg.toString())

            holder.TextView_Number_Cart.setText(soluongg.toString())

            var activity : Activity = context as Activity
            (activity as NavigationActivity).ChangeFrag(2)

            if(soluongg < 1 || soluongg == 1){
                holder.ImageView_minus.visibility = View.INVISIBLE
            }
        }


        return view as View
    }

//    fun countt (totalAllne : TextView){
//        totalAllne.setText(TotalAllne.toString())
//    }

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
    fun putData(position : Int){
        val intent = Intent(context, Content_Shop_Activity::class.java)
        intent.putExtra("hinhh",cart_list.get(position).Hinh)
        intent.putExtra("tenn",cart_list.get(position).Ten)
        intent.putExtra("giaa",cart_list.get(position).Gia)
        intent.putExtra("motaa",cart_list.get(position).MoTa)
        intent.putExtra("thanhphann",cart_list.get(position).ThanhPhan)
        intent.putExtra("doituongg",cart_list.get(position).DoiTuong)
        intent.putExtra("baoquann",cart_list.get(position).BaoQuan)
        intent.putExtra("uidd",cart_list.get(position).uidd)
        context.startActivity(intent)
    }
}
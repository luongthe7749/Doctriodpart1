package com.example.doctriodpart1.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.doctriodpart1.Layout_Fragment.FragmentHome
import com.example.doctriodpart1.NavigationActivity
import com.example.doctriodpart1.Objects.*
import com.example.doctriodpart1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cart.*
import java.math.BigDecimal
import java.text.DecimalFormat

class Fragment_Cart : Fragment(){

    lateinit var mDatabase : DatabaseReference
    lateinit var mDatabase2 : DatabaseReference
    private lateinit var auth: FirebaseAuth
    var Title : String? = ""
    var Detail : String? = ""
    var UrlImage : String? = ""
    var values : String? = ""
    var Element : String? = ""
    var Object : String? = ""
    var Protect : String? = ""
    var Uidd : String? = ""
    var Number : String? = ""
    var Total : String? = ""
    var TotalAll : Long = 0
    lateinit var mangg : ArrayList<Cart>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_cart,container,false)
        (activity as NavigationActivity).supportActionBar!!.hide()

        val ListView_Cart : ListView = view.findViewById(R.id.listView_Cart) as ListView
        val TextViewTotalAll : TextView = view.findViewById(R.id.textViewTotalAll) as TextView
        val Button__book_cart : Button = view.findViewById(R.id.button__book_cart) as Button
        val TTextView_ThanhTien : TextView = view.findViewById(R.id.TextView_ThanhTien)

        mangg = ArrayList()

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid!!).child("Cart")
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid!!)

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    UrlImage  = dataSnapshot1.child("a_hinhh").value as String
                    Title  = dataSnapshot1.child("b_tenn").value as String
                    values = dataSnapshot1.child("c_giaa").value as String
                    Detail = dataSnapshot1.child("d_motaa").value as String
                    Element = dataSnapshot1.child("e_thanhphann").value as String
                    Object = dataSnapshot1.child("f_doituongg").value as String
                    Protect = dataSnapshot1.child("g_baoquann").value as String
                    Uidd = dataSnapshot1.child("h_uid").value as String
                    Number = dataSnapshot1.child("i_soluong").value as String
                    Total = dataSnapshot1.child("j_tong").value as String

                    mangg.add(
                        Cart(UrlImage.toString(),Title.toString(),values.toString(),Detail.toString(),
                            Element.toString(),Object.toString(),Protect.toString(), Uidd.toString(),
                            Number.toString(),Total.toString())
                    )

                        TotalAll += Total!!.toLong()


                    setListViewHeightBasedOnChildren(ListView_Cart)
                    ListView_Cart.adapter = Cart_Adapter(view.context,R.layout.dong_cart,mangg)
                }

                var totalall : String = fmNumber(TotalAll)
                var thanhtien : Long = TotalAll + 30000
                var thanhtienn : String = fmNumber(thanhtien)

                TTextView_ThanhTien.setText(thanhtienn)
                TextViewTotalAll.setText(totalall)

                Button__book_cart.setOnClickListener {
                    mDatabase2.child("total_price").setValue(TotalAll)
                    Toast.makeText(view.context,"ok",Toast.LENGTH_LONG).show()
                }

            }

        })


        return view
    }

    fun setListViewHeightBasedOnChildren(listView : ListView) {
        val listAdapter = listView.adapter ?: // pre-condition
        return

        var totalHeight : Int
        val items = listAdapter.count
        var rows : Int

        val listItem : View = listAdapter.getView(0, null, listView)
        listItem.measure(0, 0)
        totalHeight = listItem.measuredHeight

//        var x: Float
//        if (items > columns) {
//            x = (items / columns).toFloat()
//            rows = (x + 1).toInt()
//            totalHeight *= (rows-1)
//        }

        val params :ViewGroup.LayoutParams = listView.layoutParams
        params.height = totalHeight*items
        listView.layoutParams = params

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
}
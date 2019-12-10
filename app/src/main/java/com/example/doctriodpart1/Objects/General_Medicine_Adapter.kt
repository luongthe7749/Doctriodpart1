package com.example.doctriodpart1.Objects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.doctriodpart1.R
import java.math.BigDecimal
import java.text.DecimalFormat

class General_Medicine_Adapter(var context: Context, var layout : Int, var general_list : ArrayList<General_Medicine>) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return general_list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return general_list.size
    }


    class ViewHolder(view: View){
        var ImageView_HinhMedicine : ImageView
        var TextView_TenMedicine : TextView
        var TextView_ValueMedicine : TextView
        init {
            ImageView_HinhMedicine =  view.findViewById(R.id.imageView_HinhMedicine) as ImageView
            TextView_TenMedicine = view.findViewById(R.id.textView_TenMedicine) as TextView
            TextView_ValueMedicine = view.findViewById(R.id.textView_ValueMedicine) as TextView
        }


    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var holder : General_Medicine_Adapter.ViewHolder
        if(convertView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view =  layoutInflater.inflate(layout,null)
            holder = General_Medicine_Adapter.ViewHolder(view)
            view.setTag(holder)
        }else{
            view = convertView
            holder = convertView.getTag() as General_Medicine_Adapter.ViewHolder
        }
        var medicine : General_Medicine = getItem(position) as General_Medicine
        Glide.with(context).load(medicine.Hinh).into(holder.ImageView_HinhMedicine)
        holder.TextView_TenMedicine.setText(medicine.Ten)

        val giaa : Long = medicine.Gia.toLong()
        val giaaa : String = fmNumber(giaa)
        holder.TextView_ValueMedicine.setText(giaaa)
        return view as View
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
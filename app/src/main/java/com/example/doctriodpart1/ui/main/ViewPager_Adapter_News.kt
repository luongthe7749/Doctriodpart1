package com.example.doctriodpart1.ui.main

import android.content.Context
import android.content.Intent
import android.media.Image
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Content_News_Activity
import com.example.doctriodpart1.Objects.News
import com.example.doctriodpart1.R

class ViewPager_Adapter_News(val context: Context, val mang : List<News>) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null


    override fun isViewFromObject(view: View, objectt : Any): Boolean {
        return view ===  objectt
    }

    override fun getCount(): Int {
        return mang.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.custom_layout_viewpager_news,null)
        val imageee2 = view.findViewById<View>(R.id.imageee2) as ImageView
        val Nameee = view.findViewById<View>(R.id.nameee) as TextView

        Glide.with(context).load(mang[position].Hinh).into(imageee2)
        Nameee.setText(mang.get(position).Ten)

        view.setOnClickListener{
            when(position){
                0 ->{
                    PutDataa(0)
                }
                1 ->{
                    PutDataa(1)
                }
                2 ->{
                    PutDataa(2)
                }
                3 ->{
                    PutDataa(3)
                }
                4 ->{
                    PutDataa(4)
                }
            }
        }


        val vp : ViewPager = container as ViewPager
        vp.addView(view,0)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, oobject: Any) {
        val vp : ViewPager = container as ViewPager
        val view : View = oobject as View
        vp.removeView(view)
    }


    fun PutDataa (thutu : Int){
        val intent = Intent(context, Content_News_Activity::class.java)
        intent.putExtra("hinhna",mang.get(thutu).Hinh)
        intent.putExtra("titlena",mang.get(thutu).Ten)
        intent.putExtra("contentna",mang.get(thutu).Mota)

        context.startActivity(intent)
    }
}
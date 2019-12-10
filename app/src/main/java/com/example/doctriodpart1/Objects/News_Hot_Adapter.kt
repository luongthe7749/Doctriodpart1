package com.example.doctriodpart1.Objects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.R
import kotlinx.android.synthetic.main.dong_doctor_photos.view.*

class News_Hot_Adapter(var context: Context, var layout : Int, var List : ArrayList<News>) : BaseAdapter() {

    override fun getCount(): Int {
        return List.size
    }
    override fun getItem(position: Int): Any {
        return List.get(position)
    }

    override fun getItemId(position: Int): Long {
//        Toast.makeText(context,"ban chon: "+photoList.get(position),Toast.LENGTH_LONG).show()
        return 0
    }

    class ViewHolder(view: View){
        var Img_photos_News_List : ImageView
        var TextViewTitle_news_list : TextView
        init {
            Img_photos_News_List =  view.findViewById(R.id.imageView_news_list) as ImageView
            TextViewTitle_news_list = view.findViewById(R.id.textViewTitle_news_list) as TextView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var holder :ViewHolder
        if(convertView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view =  layoutInflater.inflate(layout,null)
            holder = ViewHolder(view)
            view.setTag(holder)
        }else{
            view = convertView
            holder = convertView.getTag() as ViewHolder
        }
        var news : News = getItem(position) as News
        Glide.with(context).load(news.Hinh).into(holder.Img_photos_News_List)
        holder.TextViewTitle_news_list.text = news.Ten
        return view as View
    }
}
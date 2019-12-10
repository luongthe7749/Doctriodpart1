package com.example.doctriodpart1.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.doctriodpart1.Content_News_Activity
import com.example.doctriodpart1.Objects.News
import com.example.doctriodpart1.Objects.News_Hot_Adapter
import com.example.doctriodpart1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Fragment_News2 : Fragment() {
    lateinit var mDatabase : DatabaseReference
    var title : String? = ""
    var content : String? = ""
    var UrlImage : String? = ""

    lateinit var mang3 : ArrayList<News>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_news2,container,false)

        val ImageView_Global : ImageView = view.findViewById(R.id.imageView_Global)
        val Namee_global : TextView = view.findViewById(R.id.namee_global)
        val ListView_News_Global : ListView = view.findViewById(R.id.listView_Global) as ListView
        val Rela_global : RelativeLayout = view.findViewById(R.id.rela_global)

        mang3 = ArrayList()

        val mdatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("News").child("Global").child("List_Global")
        mdatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                     UrlImage  = dataSnapshot1.child("a_image").value as String
                     title  = dataSnapshot1.child("b_title").value as String
                     content = dataSnapshot1.child("c_content").value as String

                    mang3.add(News(UrlImage.toString(),title.toString(),content.toString()))

                    val adapter3 = News_Hot_Adapter(view.context,R.layout.dong_news_hot,mang3)
                    ListView_News_Global.adapter = adapter3
                }
                setListViewHeightBasedOnChildren(ListView_News_Global,1)

                ListView_News_Global.setOnItemClickListener({ parent, view, position, id ->
//                    Toast.makeText(view.context,""+position,Toast.LENGTH_LONG).show()
                    val intent = Intent(view.context,Content_News_Activity::class.java)
                    intent.putExtra("hinhna",mang3.get(position).Hinh)
                    intent.putExtra("titlena",mang3.get(position).Ten)
                    intent.putExtra("contentna",mang3.get(position).Mota)

                    startActivity(intent)

                })

                }

        })

        // get data users

        mDatabase = FirebaseDatabase.getInstance().getReference().child("News").child("Global").child("banner")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {


                title  = data?.child("title")?.value?.toString()
                UrlImage = data?.child("image")?.value?.toString()
                content = data?.child("content")?.value?.toString()
//                Log.d("anhthe12345","ten: "+title.toString())

                Glide.with(view.context).load(UrlImage).into(ImageView_Global)
                Namee_global.text = title

            }
        })

        Rela_global.setOnClickListener{
            val intent : Intent = Intent(view.context, Content_News_Activity::class.java)
            intent.putExtra("titlena",title)
            intent.putExtra("hinhna",UrlImage)
            intent.putExtra("contentna",content)

            startActivity(intent)

        }




        val ScrollViewww : ScrollView = view.findViewById(R.id.scrollBar_News_Global)

        ScrollViewww.smoothScrollTo(0,0)

        ListView_News_Global.setFocusable(false);

        return view
    }

    fun setListViewHeightBasedOnChildren(listView : ListView, columns: Int) {
        val listAdapter = listView.adapter ?: // pre-condition
        return

        var totalHeight : Int
        val items = listAdapter.count
        var rows : Int

        val listItem : View = listAdapter.getView(0, null, listView)
        listItem.measure(0, 0)
        totalHeight = listItem.measuredHeight

        var x: Float
        if (items > columns) {
            x = (items / columns).toFloat()
            rows = (x + 1).toInt()
            totalHeight *= (rows-1)
        }

        val params :ViewGroup.LayoutParams = listView.layoutParams
        params.height = totalHeight + 50
        listView.layoutParams = params

    }
}
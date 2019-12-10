package com.example.doctriodpart1.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ScrollView
import com.example.doctriodpart1.Content_News_Activity
import com.example.doctriodpart1.Objects.News
import com.example.doctriodpart1.Objects.News_Hot_Adapter
import com.example.doctriodpart1.R
import com.github.demono.AutoScrollViewPager
import com.google.firebase.database.*

class Fragment_News1 : Fragment() {
    internal lateinit var viewPager2 : AutoScrollViewPager
    var sliderDotspane2: LinearLayout? = null
    private var dotscount: Int = 0
    private var dots: Array<ImageView?>? = null

    lateinit var mang : ArrayList<News>
    lateinit var mang2 : ArrayList<News>

    lateinit var mDatabase : DatabaseReference
    var title : String? = ""
    var content : String? = ""
    var UrlImage : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_news1,container,false)

        viewPager2 = view.findViewById(R.id.viewPager2) as AutoScrollViewPager
        sliderDotspane2 = view.findViewById(R.id.SliderDots2) as LinearLayout
        val ListView_News_Hot : ListView = view.findViewById(R.id.listView_News_Hot) as ListView

        mang = ArrayList()

        val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("News").child("Hot").child("banner")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    UrlImage  = dataSnapshot1.child("a_image").value as String
                    title  = dataSnapshot1.child("b_title").value as String
                    content = dataSnapshot1.child("c_content").value as String

                    mang.add(News(UrlImage.toString(),title.toString(),content.toString()))

                    val vadapter  = ViewPager_Adapter_News(view.context, mang)
                    viewPager2.adapter = vadapter

                    dotscount = vadapter.getCount()
                    dots = arrayOfNulls(dotscount)

                }


                for (i in 0 until dotscount) {

                    dots!![i] = ImageView(view.context)
                    dots!![i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            view.context,
                            R.drawable.active_dots
                        )
                    )

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    params.setMargins(8, 0, 8, 0)

                    sliderDotspane2!!.addView(dots!![i], params)

                }

                dots!![0]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        view.context,
                        R.drawable.nonactive_dots
                    )
                )

                viewPager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }

                    override fun onPageSelected(position: Int) {

                        for (i in 0 until dotscount) {
                            dots!![i]?.setImageDrawable(
                                ContextCompat.getDrawable(
                                    view.context,
                                    R.drawable.active_dots
                                )
                            )
                        }

                        dots!![position]?.setImageDrawable(
                            ContextCompat.getDrawable(
                                view.context,
                                R.drawable.nonactive_dots
                            )
                        )

                    }

                    override fun onPageScrollStateChanged(state: Int) {

                    }
                })

                viewPager2.startAutoScroll()
                viewPager2.setCycle(true)


            }

        })



        mang2 = ArrayList()

        val mdatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("News").child("Hot").child("List_Hot")
        mdatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(data : DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                for (dataSnapshot1 in data.getChildren()) {
//
                    UrlImage  = dataSnapshot1.child("a_image").value as String
                    title  = dataSnapshot1.child("b_title").value as String
                    content = dataSnapshot1.child("c_content").value as String

                    mang2.add(News(UrlImage.toString(),title.toString(),content.toString()))

                    val adapter2 = News_Hot_Adapter(view.context,R.layout.dong_news_hot,mang2)
                    ListView_News_Hot.adapter = adapter2
                }
                setListViewHeightBasedOnChildren(ListView_News_Hot,1)

                ListView_News_Hot.setOnItemClickListener({ parent, view, position, id ->
                    //                    Toast.makeText(view.context,""+position,Toast.LENGTH_LONG).show()
                    val intent = Intent(view.context, Content_News_Activity::class.java)
                    intent.putExtra("hinhna",mang2.get(position).Hinh)
                    intent.putExtra("titlena",mang2.get(position).Ten)
                    intent.putExtra("contentna",mang2.get(position).Mota)

                    startActivity(intent)

                })

            }

        })

//        val vadapter  = ViewPager_Adapter_News(view.context, mang)

//        dotscount = vadapter.getCount()
//        dots = arrayOfNulls(dotscount)
//
//
//        for (i in 0 until dotscount) {
//
//            dots!![i] = ImageView(view.context)
//            dots!![i]?.setImageDrawable(
//                ContextCompat.getDrawable(
//                    view.context,
//                    R.drawable.active_dots
//                )
//            )
//
//            val params = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//
//            params.setMargins(8, 0, 8, 0)
//
//            sliderDotspane2!!.addView(dots!![i], params)
//
//        }
//
//        dots!![0]?.setImageDrawable(
//            ContextCompat.getDrawable(
//                view.context,
//                R.drawable.nonactive_dots
//            )
//        )
//
//        viewPager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
//
//                for (i in 0 until dotscount) {
//                    dots!![i]?.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            view.context,
//                            R.drawable.active_dots
//                        )
//                    )
//                }
//
//                dots!![position]?.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        view.context,
//                        R.drawable.nonactive_dots
//                    )
//                )
//
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//        })
//
//        viewPager2.startAutoScroll()
//        viewPager2.setCycle(true)

        setListViewHeightBasedOnChildren(ListView_News_Hot,1)

        val ScrollVieww : ScrollView = view.findViewById(R.id.scrollBar_News_Hot)

        ScrollVieww.smoothScrollTo(0,0)

        ListView_News_Hot.setFocusable(false);

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
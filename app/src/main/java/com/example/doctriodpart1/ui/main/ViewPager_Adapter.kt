package com.example.doctriodpart1.ui.main

import android.content.Context
import android.media.Image
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doctriodpart1.R

class ViewPager_Adapter(val context: Context) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null
    val resources : List<String> = listOf(
        "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/medicine_shop%2Fbanner%2Fbanner_medicine_shop_one.jpg?alt=media&token=dcbb70fd-5d4f-484b-aefb-3d3736ed2af0",
        "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/medicine_shop%2Fbanner%2Fbanner_medicine_shop_two.jpg?alt=media&token=1acc5b4d-d856-47c4-bbed-999b8eab97b8",
        "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/medicine_shop%2Fbanner%2Fbanner_medicine_shop_three.jpg?alt=media&token=6cf9622f-f717-4cce-9fe4-156bf08563f4",
        "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/medicine_shop%2Fbanner%2Fbanner_medicine_shop_four.jpg?alt=media&token=59b16129-2d0f-4526-a635-c7086e4c1c4f",
        "https://firebasestorage.googleapis.com/v0/b/testfirebaselogin-4453c.appspot.com/o/medicine_shop%2Fbanner%2Fbanner_medicine_shop_five.jpg?alt=media&token=b7e9393a-32ad-4a74-b89d-d59394d07bca"
        )


    override fun isViewFromObject(view: View, objectt : Any): Boolean {
        return view ===  objectt
    }

    override fun getCount(): Int {
        return resources.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.custom_layout_viewpager,null)
        val imageee = view.findViewById<View>(R.id.imageee) as ImageView
        Glide.with(context).load(resources[position]).into(imageee)

        view.setOnClickListener{
            when(position){
                0 ->{
                    Toast.makeText(context,"Slide 1",Toast.LENGTH_LONG).show()
                }
                1 ->{
                    Toast.makeText(context,"Slide 2",Toast.LENGTH_LONG).show()
                }
                2 ->{
                    Toast.makeText(context,"Slide 3",Toast.LENGTH_LONG).show()
                }
                3 ->{
                    Toast.makeText(context,"Slide 4",Toast.LENGTH_LONG).show()
                }
                4 ->{
                    Toast.makeText(context,"Slide 5",Toast.LENGTH_LONG).show()
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
}
package com.example.doctriodpart1

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.doctriodpart1.ui.main.SectionsPagerAdapter
import com.example.doctriodpart1.ui.main.ViewPager_Adapter
import com.github.demono.AutoScrollViewPager
import kotlinx.android.synthetic.main.activity_tab__layout__shop_.*

class Tab_Layout_Shop_Activity : AppCompatActivity() {
//    lateinit var mViewFlipper: ViewFlipper
//    lateinit var mGestureDetector: GestureDetector

    internal lateinit var viewPager1 : AutoScrollViewPager
    var sliderDotspanel: LinearLayout? = null
    private var dotscount: Int = 0
    private var dots: Array<ImageView?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab__layout__shop_)


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        var viewPager: ViewPager = findViewById(R.id.view_pager)



        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        backk_medicine_shop.setOnClickListener {
            onBackPressed()
            finish()
        }
        cart_medicine_shop.setOnClickListener {
            val intent = Intent(applicationContext,NavigationActivity::class.java)
            intent.putExtra("oki","kio")
            startActivity(intent)
        }

        viewPager1 = findViewById(R.id.viewPager1) as AutoScrollViewPager
        sliderDotspanel = findViewById(R.id.SliderDots) as LinearLayout


        val vadapter  = ViewPager_Adapter(applicationContext)
        viewPager1.adapter = vadapter

        dotscount = vadapter.getCount()
        dots = arrayOfNulls(dotscount)

        for (i in 0 until dotscount) {

            dots!![i] = ImageView(this)
            dots!![i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.active_dots
                )
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(8, 0, 8, 0)

            sliderDotspanel!!.addView(dots!![i], params)

        }
        dots!![0]?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.nonactive_dots
            )
        )


        viewPager1.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                for (i in 0 until dotscount) {
                    dots!![i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.active_dots
                        )
                    )
                }

                dots!![position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.nonactive_dots
                    )
                )

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        viewPager1.startAutoScroll()
        viewPager1.setCycle(true)




//        setGridViewHeightBasedOnChildren(GridView_GeneralMedicine,3)


//
        val ScrollVieww : ScrollView = findViewById(R.id.scrollBar_GeneralMedicine)

        ScrollVieww.smoothScrollTo(0,0)

    }

}

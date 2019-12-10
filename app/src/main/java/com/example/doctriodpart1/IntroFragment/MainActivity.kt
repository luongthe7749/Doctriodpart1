package com.example.doctriodpart1.IntroFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toast
import com.example.doctriodpart1.DashBoardActivity
import com.example.doctriodpart1.R
import com.karan.churi.PermissionManager.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view_pager

class MainActivity : AppCompatActivity() {

    lateinit var permissionManager: PermissionManager

    val fragment1 = BlankFragment1()
    val fragment2 = BlankFragment2()
    val fragment3 = BlankFragment3()
    lateinit var adapter : myPagerAdapter
    lateinit var activity: Activity

    // share preferences
    lateinit var Preferences: SharedPreferences
    val show_intro = "Show"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionManager = object : PermissionManager() {}
        permissionManager.checkAndRequestPermissions(this)

        activity = this

        // share preferences
        Preferences = getSharedPreferences("Thedzai", MODE_PRIVATE)
        if(!Preferences.getBoolean(show_intro,true)){
            startActivity(Intent(applicationContext, DashBoardActivity::class.java))
            finish()

        }

        adapter = myPagerAdapter(
            supportFragmentManager
        )
        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)

        view_pager.adapter = adapter
        button_Next.setOnClickListener {
            view_pager.currentItem++
        }
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                if(position == adapter.list.size-1){
                    // last page
                    button_Next.visibility = View.INVISIBLE
                    button_Skip.visibility = View.INVISIBLE
                    linear_dot.visibility = View.INVISIBLE

                    button_start.visibility = View.VISIBLE

                    button_start.setOnClickListener {

                        // share preferences
                        val editor = Preferences.edit()
                        editor.putBoolean(show_intro,false)
                        editor.apply()


                        startActivity(Intent(activity, DashBoardActivity::class.java))
                        finish()

                    }
                } else{
                    // has next
                    button_Next.visibility = View.VISIBLE
                    button_Skip.visibility = View.VISIBLE
                    linear_dot.visibility = View.VISIBLE

                    button_start.visibility = View.INVISIBLE

                    button_Next.setOnClickListener {
                        view_pager.currentItem++
                    }
                    button_Skip.setOnClickListener {
                        view_pager.currentItem--
                    }
                }

                when (view_pager.currentItem){
                    0 ->{
                        indicator1.setTextColor(Color.BLACK)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    1->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.BLACK)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    2->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.BLACK)
                    }
                }
            }

        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionManager.checkResult(requestCode, permissions, grantResults)

        val grandted = permissionManager.status[0].granted
        val denied = permissionManager.status[0].denied

    }



    class myPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager){

        val list : MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

    }
}

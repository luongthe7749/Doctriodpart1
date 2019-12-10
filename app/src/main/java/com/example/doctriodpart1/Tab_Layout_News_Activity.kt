package com.example.doctriodpart1

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.doctriodpart1.ui.main.SectionsPagerAdapter
import com.example.doctriodpart1.ui.main.SectionsPagerAdapter_News
import com.example.doctriodpart1.ui.main.ViewPager_Adapter
import com.example.doctriodpart1.ui.main.ViewPager_Adapter_News
import com.github.demono.AutoScrollViewPager
import kotlinx.android.synthetic.main.activity_tab__layout__news_.*

class Tab_Layout_News_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab__layout__news_)

        val SectionsPagerAdapter_News = SectionsPagerAdapter_News(this, supportFragmentManager)
        val View_pager_news: ViewPager = findViewById(R.id.view_pager_news)
        View_pager_news.adapter = SectionsPagerAdapter_News
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(View_pager_news)

        imageView_Back_News.setOnClickListener {
            onBackPressed()
            finish()
        }

    }
}
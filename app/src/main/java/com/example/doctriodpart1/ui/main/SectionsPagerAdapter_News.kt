package com.example.doctriodpart1.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.doctriodpart1.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_news1,
    R.string.tab_text_news2,
    R.string.tab_text_news3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter_News(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        var frag : Fragment? = null
        when(position){
            0 ->{
                frag =  Fragment_News1()
            }
            1 -> {
                frag = Fragment_News2()
            }
            2 -> {
                frag = Fragment_News3()
            }
        }
        return frag!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])

    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}
package com.example.doctriodpart1.IntroFragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctriodpart1.R


class BlankFragment1 : Fragment() {

    var pageTitle : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.intro_slider_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    fun SetTitle(title: String){
        pageTitle = title
    }

}

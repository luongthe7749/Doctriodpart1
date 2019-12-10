package com.example.doctriodpart1.Layout_Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctriodpart1.R

class Fragment1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val frag = Fragment2()
        frag.change("the")

        return LayoutInflater.from(container?.context).inflate(R.layout.first_layout,container,false)
}

}
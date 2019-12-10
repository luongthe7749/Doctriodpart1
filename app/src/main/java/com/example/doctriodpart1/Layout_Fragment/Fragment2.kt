package com.example.doctriodpart1.Layout_Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.doctriodpart1.R

class Fragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = LayoutInflater.from(container?.context).inflate(R.layout.second_layout, container, false)
        return view
    }
    fun change(ten : String){
        Log.d("the123",ten)
    }
}
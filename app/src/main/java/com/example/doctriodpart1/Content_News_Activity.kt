package com.example.doctriodpart1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content__news_.*

class Content_News_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content__news_)
        val intent : Intent = getIntent()
        val title: String = intent.getStringExtra("titlena")
        val urlImage: String = intent.getStringExtra("hinhna")
        val content: String = intent.getStringExtra("contentna")

        if(title != null || urlImage != null || content!= null){
            Glide.with(applicationContext).load(urlImage).into(imageView_Content)
            namee_content.text = title
            content_Content.text = content
        }

    }
}

package com.baranonat.besinkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.baranonat.besinkitabi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.gorselIndir(url:String?,placeHolder:CircularProgressDrawable){
val options=RequestOptions().placeholder(placeHolder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

    fun placeHolderYap(contex:Context):CircularProgressDrawable{

        return CircularProgressDrawable(contex).apply {
            strokeWidth=8f
            centerRadius=40f
            start()
        }
    }
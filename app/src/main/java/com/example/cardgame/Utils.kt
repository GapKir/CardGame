package com.example.cardgame

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

fun View.setImageToImageView(value: Int){
    (this as ViewGroup)
        .findViewById<ImageView>(R.id.iv_card)
        .setImageResource(value)
}
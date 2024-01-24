package com.example.cardgame.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.cardgame.R

fun View.setImageToImageView(value: Int){
    (this as ViewGroup)
        .findViewById<ImageView>(R.id.iv_card)
        .setImageResource(value)
}

fun Fragment.navigator(): Navigator = this.requireActivity() as Navigator
fun Fragment.toast(resId: Int){
    Toast.makeText(this.requireContext(), resId, Toast.LENGTH_SHORT).show()
}
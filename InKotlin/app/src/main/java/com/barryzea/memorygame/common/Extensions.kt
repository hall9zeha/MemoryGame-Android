package com.barryzea.memorygame.common

import android.app.ActionBar
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.barryzea.memorygame.R

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

fun createLinearLayout(ctx:Context):LinearLayout{
    val lnRow=LinearLayout(ctx)
    val params = LinearLayout.LayoutParams(
        ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT,1.0f)
    //lnRow.layoutParams=params
    lnRow.orientation=LinearLayout.HORIZONTAL
    lnRow.gravity= Gravity.CENTER
    lnRow.setPadding(4,4,4,4)
    return lnRow
}
fun createCardView(ctx:Context, imageResource: Int, onClick:(String)->Unit):CardView{
    val cardView= CardView(ctx)
    val cardParams = LinearLayout.LayoutParams(
        ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT,1.0f)

    val imageView = createImageView(ctx,imageResource)
    cardParams.setMargins(4,4,4,4,)
    imageView.scaleType=ImageView.ScaleType.CENTER_CROP
    cardView.layoutParams=cardParams
    cardView.cardElevation=8F
    cardView.radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, ctx.resources.displayMetrics)

    cardView.isClickable=true
    cardView.isFocusable=true
    cardView.setOnClickListener { onClick(cardView.tag.toString()) }
    imageView.setImageResource(R.drawable.ic_launcher_background)

    cardView.addView(imageView)
    return cardView
}

fun createImageView(ctx:Context, imageResource:Int):ImageView{
    val imageView = ImageView(ctx)
    imageView.scaleType=ImageView.ScaleType.CENTER_CROP
    imageView.setImageResource(imageResource)
    return imageView
}
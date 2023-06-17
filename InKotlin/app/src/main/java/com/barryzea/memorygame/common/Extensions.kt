package com.barryzea.memorygame.common

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.entities.Card
import com.barryzea.memorygame.common.entities.GameImage

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
fun createCardView(ctx:Context,tag:String, imageEntity:GameImage, onClick:(Card)->Unit):CardView{
    val cardView= CardView(ctx)
    val cardParams = LinearLayout.LayoutParams(
        ActionBar.LayoutParams.MATCH_PARENT,ctx.resources.getDimensionPixelSize(R.dimen.height),1.0f)
    val imageView = createImageView(ctx,imageEntity.imageRes)
    cardParams.setMargins(8,8,8,8)
    cardView.layoutParams=cardParams
    cardView.cardElevation=8F
    cardView.radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, ctx.resources.displayMetrics)
    cardView.contentDescription=imageEntity.name
    cardView.tag=tag
    cardView.isClickable=true
    cardView.isFocusable=true
    cardView.setOnClickListener {
        onClick(Card(coordinates = cardView.tag.toString(),
        description = cardView.contentDescription.toString())) }
    cardView.addView(imageView)
    return cardView
}

fun createImageView(ctx:Context, imageResource:Int):ImageView{
    val imageView = ImageView(ctx)
    imageView.scaleType=ImageView.ScaleType.CENTER_INSIDE
    imageView.setPadding(4,4,4,4)
    imageView.setImageResource(imageResource)
    return imageView
}

inline fun <reified T:Activity> Context.gotoActivity(body:Intent.()->Unit){
    startActivity(intentFor<T>(body))
}
inline fun <reified T:Activity> Context.intentFor(body: Intent.()->Unit):Intent=
    Intent(this,T::class.java).apply(body)

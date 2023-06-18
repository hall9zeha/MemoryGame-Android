package com.barryzea.memorygame.common

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.entities.Card
import com.barryzea.memorygame.common.entities.GameImage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

fun createLinearLayout(ctx:Context):LinearLayout{
    val lnRow=LinearLayout(ctx)
    val lnParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f)
    lnRow.layoutParams=lnParams
    lnRow.orientation=LinearLayout.HORIZONTAL
    lnRow.gravity= Gravity.CENTER
    lnRow.setPadding(4,4,4,4)
    return lnRow
}
fun createCardView(ctx:Context,tag:String, imageEntity:GameImage, onClick:(Card)->Unit):CardView{
    val cardView= CardView(ctx)
    /*val cardParams = LinearLayout.LayoutParams(
        ActionBar.LayoutParams.MATCH_PARENT,ctx.resources.getDimensionPixelSize(R.dimen.height),1.0f)*/
    val cardParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f)
    val imageViewFront= createImageView(ctx,R.drawable.aleph)
    imageViewFront.id="0".toInt()
    val imageViewBack = createImageView(ctx,imageEntity.imageRes)
    imageViewBack.visibility=View.GONE
    imageViewBack.id="1".toInt()

    cardParams.setMargins(8,8,8,8)
    cardView.layoutParams=cardParams
    cardView.cardElevation=8F
    cardView.radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, ctx.resources.displayMetrics)
    cardView.contentDescription=imageEntity.name
    cardView.tag=tag
    cardView.isClickable=true
    cardView.isFocusable=true
    cardView.setOnClickListener {
        onClick(Card(coordinates = getCoordinates(cardView.tag.toString()),
        description = cardView.contentDescription.toString())) }

    cardView.addView(imageViewBack)
    cardView.addView(imageViewFront)
    return cardView
}
fun getCoordinates(tag:String):Pair<Int,Int>{
    val x = tag.substringBefore(",").toInt()
    val y = tag.substringAfter(",").toInt()
    return Pair(x,y)
}
fun createImageView(ctx:Context, imageResource:Int):ImageView{
    val imageView = ImageView(ctx)
    imageView.scaleType=ImageView.ScaleType.CENTER_INSIDE
    imageView.setPadding(4,4,4,4)
    imageView.setImageResource(imageResource)

    return imageView
}
fun Context.showGameDialog(msg:Int){
    MaterialAlertDialogBuilder(this)
        .setMessage(msg)
        .setPositiveButton(getString(R.string.accept)){d,_->
            d.dismiss()
        }
        .show()
}
inline fun <reified T:Activity> Context.gotoActivity(body:Intent.()->Unit){
    startActivity(intentFor<T>(body))
}
inline fun <reified T:Activity> Context.intentFor(body: Intent.()->Unit):Intent=
    Intent(this,T::class.java).apply(body)
fun <T:Any>Activity.textFormatted(value:T,msg:String)=String.format("%s %s",msg,value)

fun postDelay(delayMillis:Long,block:()->Unit){
    Handler(Looper.getMainLooper()).postDelayed({block()}, delayMillis)
}
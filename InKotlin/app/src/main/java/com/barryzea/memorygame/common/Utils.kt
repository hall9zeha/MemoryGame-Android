package com.barryzea.memorygame.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.entities.Card
import com.barryzea.memorygame.common.entities.ImageGame
import com.barryzea.memorygame.databinding.DialogLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.TimeUnit

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
fun createCardView(ctx:Context, tag:String, imageEntity:ImageGame, onClick:(Card)->Unit):CardView{
    val cardView= CardView(ctx)
    val cardParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f)
    val imageViewFront= createImageView(ctx,R.drawable.aleph)
    imageViewFront.scaleType = ImageView.ScaleType.FIT_XY
    imageViewFront.id="0".toInt()
    val imageViewBack = createImageView(ctx,imageEntity.imageRes)
    imageViewBack.id="1".toInt()
    // Mostramos las imágenes al inicio para que el usuario intente memorizarlas
    // estas se ocultarán después de unos segundos (en la función setUpGameBoard() del viewModel)
    imageViewFront.visibility=View.GONE
    imageViewBack.visibility=View.VISIBLE

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
fun Context.showGameDialog(msg:Int, imgRes:Int,onRetryClick:()->Unit){
    val bind=DialogLayoutBinding.inflate((this as AppCompatActivity).layoutInflater)
    bind.tvDialog.text=this.getString(msg)
    bind.ivDialog.loadImageRes(imgRes)
    MaterialAlertDialogBuilder(this)
        .setView(bind.root)
        .setCancelable(false)
        .setPositiveButton(getString(R.string.retry)){ d, _->
            onRetryClick()
            d.dismiss()
        }
        .setNegativeButton(getString(R.string.accept)){ d, _->
            d.dismiss()
        }
        .show()

}
fun ImageView.loadImageRes(resource:Int){
    Glide.with(this)
        .load(resource)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerInside()
        .into(this)
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
fun getTimeFormatted(millis:Long):String{
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    return String.format("%02d:%02d",minutes,seconds)
}
fun createSound(ctx:Context, soundRes:Int){
    val mp = MediaPlayer.create(ctx,soundRes)
    mp.start()
    mp.setOnCompletionListener {mp.release()}
}
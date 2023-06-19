package com.barryzea.memorygame.viewModel

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.media.Image
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.DummyDataSource
import com.barryzea.memorygame.common.SingleMutableLiveData
import com.barryzea.memorygame.common.createCardView
import com.barryzea.memorygame.common.createLinearLayout
import com.barryzea.memorygame.common.entities.Card
import com.barryzea.memorygame.common.entities.GameImage
import com.barryzea.memorygame.common.getCoordinates

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

class GameViewModel : ViewModel() {

   lateinit var  frontAnimator: AnimatorSet
   lateinit var  backAnimator: AnimatorSet
   private var timer:CountDownTimer? = null
    var imagesArray:Array<Array<Card>> = arrayOf()

    private var _viewCreated:SingleMutableLiveData<LinearLayout> = SingleMutableLiveData()
    val viewCreated:SingleMutableLiveData<LinearLayout> = _viewCreated
    private var _onCardClicked:SingleMutableLiveData<Card> = SingleMutableLiveData()
    val onCardClicked:SingleMutableLiveData<Card> = _onCardClicked
    private var _countPairs:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val countPairs:SingleMutableLiveData<Int> =_countPairs
    private var _timerCount:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val timerCount:LiveData<Int> get() = _timerCount
    private var _maxProgress:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val maxProgress:LiveData<Int> get() = _maxProgress


    fun setUpGameBoard(ctx:Context, rows:Int,columns:Int, minutes:Int){
        //X is row and Y is column
        imagesArray= Array(rows){Array(columns){Card() }}
        val randomList = DummyDataSource.getRandomList(rows*columns)
        val listForCuntPairs= mutableListOf<GameImage>()
        for(x in 0 until rows){
            val lnRow= createLinearLayout(ctx)
            for(y in 0  until columns){
                val imageEntity=randomList.random()
                listForCuntPairs.add(imageEntity)//agregamos cada imagen a la lista para contar cuantos pares hay
                val coordinatesTag=String.format("%s,%s",x,y)
                val cardView= createCardView(ctx,coordinatesTag,imageEntity){ _onCardClicked.value=it}
                lnRow.addView(cardView)
                imagesArray[x][y]= Card(coordinates = getCoordinates(coordinatesTag),
                    description = cardView.contentDescription.toString())
            }
            _viewCreated.value=lnRow
        }
        countPairsOfImages(listForCuntPairs)//llamamos a la función que contará los pares de imágenes
        setUpTimer(minutes)
    }
    private fun countPairsOfImages(imagesList:List<GameImage>){
        var listOfSearch= mutableListOf<GameImage>()
        listOfSearch.addAll(imagesList)
        var count=0
        //Ya que iremos eliminando los pares de imágenes, la lista se irá reduciendo, entonces mientras
        //el tamaño de la lista sea mayor a 1 se mantendrá el bucle
        while(listOfSearch.size>1){
            val image=listOfSearch[0]//siempre comenzamos por la primera posición
            val size=listOfSearch.size
            for(j in 1 until size){
                if(image.name==listOfSearch[j].name){
                    //Si encontramos una imagen con el mismo nombre eliminamos la imagen
                    //del por el índice en el que se encuentra así como la principal usada para comparar
                    listOfSearch.removeAt(j)
                    listOfSearch.removeAt(0)
                    count++
                    break
                }else if(j>=(size-1)){
                    //si no hay imágenes parecidas a la elegida para comparar, y ya hemos recorrido toda
                    //la lista, la eliminamos.
                    listOfSearch.removeAt(0)
                }
            }
        }
        _countPairs.value=count


    }
    private fun setUpTimer(minutes:Int){
        val timeInMillis=((minutes * 60 * 1000)).toLong()
        val maxProgressForProgressBar = (timeInMillis/1000).toInt()
        _maxProgress.value=maxProgressForProgressBar
        timer = object: CountDownTimer(timeInMillis, 1000){
            override fun onTick(millis: Long) {
               val progressPercent = millis/1000
                _timerCount.postValue(progressPercent.toInt())
            }
            override fun onFinish() {
                _timerCount.postValue(0)
            }
        }
        timer?.start()
    }
    fun stopTimer(){timer?.cancel()}

    fun setUpAnimators(ctx:Context){
        frontAnimator = AnimatorInflater.loadAnimator(ctx, R.animator.front_animator) as AnimatorSet
        backAnimator = AnimatorInflater.loadAnimator(ctx, R.animator.back_animator) as AnimatorSet
    }
    fun setAnimation(x:Int,y:Int,imgFront:Any,imgBack:Any ){
        if(!imagesArray[x][y].status){
            (imgBack as ImageView).visibility= View.VISIBLE
            frontAnimator.setTarget(imgFront);
            backAnimator.setTarget(imgBack);
            frontAnimator.start()
            backAnimator.start()
            imagesArray[x][y].status=true

        }else{
            frontAnimator.setTarget(imgBack)
            backAnimator.setTarget(imgFront)
            backAnimator.start()
            frontAnimator.start()
            imagesArray[x][y].status=false
        }
    }

}
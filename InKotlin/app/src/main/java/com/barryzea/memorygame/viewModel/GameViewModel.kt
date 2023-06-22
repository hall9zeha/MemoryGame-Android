package com.barryzea.memorygame.viewModel

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.DummyDataSource
import com.barryzea.memorygame.common.SingleMutableLiveData
import com.barryzea.memorygame.common.createCardView
import com.barryzea.memorygame.common.createLinearLayout
import com.barryzea.memorygame.common.createSound
import com.barryzea.memorygame.common.entities.Card
import com.barryzea.memorygame.common.entities.ImageGame
import com.barryzea.memorygame.common.getCoordinates
import com.barryzea.memorygame.common.postDelay

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

class GameViewModel : ViewModel() {
    private var listOfMovements:MutableList<Card> = mutableListOf()
    private var pairs=0
    private var countMovements=0
    private var timerStart=false
    @SuppressLint("StaticFieldLeak")
    private lateinit var context:Context
    private lateinit var  frontAnimator: AnimatorSet
    private lateinit var  backAnimator: AnimatorSet
    private var timer:CountDownTimer? = null
    var arrayCards:Array<Array<Card>> = arrayOf()

    private var _viewCreated:SingleMutableLiveData<LinearLayout> = SingleMutableLiveData()
    val viewCreated:SingleMutableLiveData<LinearLayout> = _viewCreated

    private var _remainingTime:MutableLiveData<Long> = MutableLiveData(0)
    val remainingTime:LiveData<Long> get() = _remainingTime
    private var _maxProgress:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val maxProgress:LiveData<Int> get() = _maxProgress

    private var _remainingPairs:MutableLiveData<Int> = MutableLiveData(0)
    val remainingPairs:LiveData<Int> get() = _remainingPairs
    private var _movements:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val movements:LiveData<Int> get() = _movements

    private var _youWin:SingleMutableLiveData<Boolean> = SingleMutableLiveData()
    val youWin:LiveData<Boolean> get() = _youWin

    fun setUpGameBoard(ctx:Context, rows:Int,columns:Int, minutes:Int){
        //X is row and Y is column
        this.context=ctx
        setUpAnimators(ctx)
        arrayCards= Array(rows){Array(columns){Card() }}
        val randomList = DummyDataSource.getRandomList(rows*columns)
        val listForCuntPairs= mutableListOf<ImageGame>()
        for(x in 0 until rows){
            val lnRow= createLinearLayout(ctx)
            for(y in 0  until columns){
                val imageRandom=randomList.random()
                listForCuntPairs.add(imageRandom)//agregamos cada imagen a la lista para contar cuantos pares hay
                val coordinatesTag=String.format("%s,%s",x,y)
                val cardView= createCardView(ctx,coordinatesTag,imageRandom){ onCardClick(it,minutes)}
                lnRow.addView(cardView)
                arrayCards[x][y]= Card(card=cardView,coordinates = getCoordinates(coordinatesTag),
                    description = cardView.contentDescription.toString())
            }
            _viewCreated.value=lnRow
            //cargamos la primera vez el tiempo a mostrar en el textView de temporizador y el 100% de progreso del progressbar
            val timeInMillis=((minutes * 60 *1000)).toLong()
            _remainingTime.postValue(timeInMillis)
            _maxProgress.postValue((timeInMillis/1000).toInt())
        }
        countPairsOfImages(listForCuntPairs)//llamamos a la función que contará los pares de imágenes

    }
    fun retryGame(ctx:Context, rows:Int,columns:Int, minutes:Int){
        timerStart=false
        setUpGameBoard(ctx,rows,columns,minutes)
    }
    private fun countPairsOfImages(imagesList:List<ImageGame>){
        val listOfSearch= mutableListOf<ImageGame>()
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
                    //por el índice en el que se encuentra así como la principal del índice 0 usada para comparar
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
        pairs=count
        _remainingPairs.postValue(pairs)
    }
    private fun initTimer(minutes:Int){
        val timeInMillis=((minutes * 60 * 1000)+1000).toLong()
        timer = object: CountDownTimer(timeInMillis, 1000){
            override fun onTick(millis: Long) {
                 _remainingTime.value=millis
            }
            override fun onFinish() {
                if(pairs>0) {//si no se encontraron todos los pares
                    _youWin.postValue(false)
                }
            }
        }
        timer?.start()
    }

    private fun onCardClick(card:Card,minutes:Int){
        if(!timerStart) {//iniciamos una sola vez el temporizador
            initTimer(minutes)
            timerStart=true}
        if(remainingPairs.value!!>0 && remainingTime.value!! >1000) {
            val x = card.coordinates.first
            val y = card.coordinates.second
            //cargamos la entidad Card en la lista
            listOfMovements.add(card)
            createSound(context,R.raw.parallel_end_soft)
            val imgFront = getCardView(x, y).findViewById<ImageView>("0".toInt())
            val imgBack = getCardView(x, y).findViewById<ImageView>("1".toInt())
            setAnimation(x, y, imgFront, imgBack)
            checkMovementsPair()
            //bloqueamos momentaneamente hasta que se haga click en otra vista para comparar
            getCardView(x, y).isEnabled = false
        }
    }
    private fun checkMovementsPair(){
        if(listOfMovements.size >1) {//si ya tenemos un par en la lista procedemos a comparar
            countMovements++
            _movements.postValue(countMovements)
            postDelay(100){
                val x = listOfMovements[0].coordinates.first
                val y = listOfMovements[0].coordinates.second
                val x1 = listOfMovements[1].coordinates.first
                val y1 = listOfMovements[1].coordinates.second
                if (arrayCards[x][y].description == arrayCards[x1][y1].description) {
                    createSound(context,R.raw.monstar_gets_key)
                    //si hay un par encontrado bloqueamos las vistas para evitar nuevos eventos en ellas
                    getCardView(x, y).isEnabled = false
                    getCardView(x1, y1).isEnabled = false
                    //actualizamos los pares restantes
                    pairs--
                    _remainingPairs.postValue(pairs)

                } else {
                    var count=1L
                    listOfMovements.forEach {
                        val xCord = it.coordinates.first
                        val yCord = it.coordinates.second
                        val imgFront = getCardView(xCord, yCord).findViewById<ImageView>("0".toInt())
                        val imgBack = getCardView(xCord, yCord).findViewById<ImageView>("1".toInt())
                        //desbloqueamos las cards si no son similares para interactuar nuevamente
                        getCardView(xCord,yCord).isEnabled=true
                        //para aplicar la animación de flipCard se requiere un retraso para cada ImageView
                        postDelay(200*count){setAnimation(xCord,yCord,imgFront,imgBack);createSound(context,R.raw.parallel_end_soft)}
                        count++
                    }
                }
                listOfMovements.clear()//limpiamos la lista para comenzar con otro par
                if(pairs==0){//si ya no quedan pares por encontrar
                    stopTimer()
                    _youWin.postValue(true)
                }
            }

        }
    }
    private fun stopTimer(){timer?.cancel()}
    private fun setUpAnimators(ctx:Context){
        frontAnimator = AnimatorInflater.loadAnimator(ctx, R.animator.front_animator) as AnimatorSet
        backAnimator = AnimatorInflater.loadAnimator(ctx, R.animator.back_animator) as AnimatorSet
    }
    private fun setAnimation(x:Int,y:Int,imgFront:Any,imgBack:Any ){
        if(!arrayCards[x][y].status){
            (imgBack as ImageView).visibility= View.VISIBLE
            frontAnimator.setTarget(imgFront);
            backAnimator.setTarget(imgBack);
            frontAnimator.start()
            backAnimator.start()
            arrayCards[x][y].status=true

        }else{
            frontAnimator.setTarget(imgBack)
            backAnimator.setTarget(imgFront)
            backAnimator.start()
            frontAnimator.start()
            arrayCards[x][y].status=false
        }
    }
    private fun getCardView(x: Int, y: Int): CardView {
        return arrayCards[x][y].card as CardView
    }
}
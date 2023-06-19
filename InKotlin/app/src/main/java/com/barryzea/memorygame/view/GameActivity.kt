package com.barryzea.memorygame.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.Constants
import com.barryzea.memorygame.common.entities.Card
import com.barryzea.memorygame.common.postDelay
import com.barryzea.memorygame.common.showGameDialog
import com.barryzea.memorygame.common.textFormatted
import com.barryzea.memorygame.databinding.ActivityGameBinding
import com.barryzea.memorygame.viewModel.GameViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGameBinding
    private val viewModel: GameViewModel by viewModels()
    private var COLUMNS=0
    private var ROWS=0
    private var listOfMovements:MutableList<Card> = mutableListOf()
    private var pairs=0
    private var movements=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpIntent()
        setUpObservers()
        setUpGameBoard()
    }

    private fun setUpIntent(){
        intent?.extras?.let{
            COLUMNS=it.getInt(Constants.COLUMN_NUM)
            ROWS=it.getInt(Constants.ROW_NUM)
        }
        setMovementsNum(0)
    }
    private fun setUpGameBoard(){
        viewModel.setUpAnimators(this)
        postDelay(500){viewModel.setUpGameBoard(this,ROWS,COLUMNS,1)}

    }
    private fun setUpObservers(){
        viewModel.viewCreated.observe(this){
            bind.lnContent.addView(it)
        }
        viewModel.onCardClicked.observe(this){
            val x = it.coordinates.first
            val y = it.coordinates.second
            //cargamos la entidad Card en la lista
            listOfMovements.add(it)

            val imgFront= getCardView(x,y).findViewById<ImageView>("0".toInt())
            val imgBack = getCardView(x,y).findViewById<ImageView>("1".toInt())
            viewModel.setAnimation(x,y,imgFront,imgBack)
            checkMovementsPair()
            //bloqueamos momentaneamente
            getCardView(x,y).isEnabled=false
        }
        viewModel.countPairs.observe(this){findPairs->
            setRemainingPairs(findPairs)
            pairs=findPairs
        }
        viewModel.timerCount.observe(this){time->
            bind.timeProgress.progress=time
            bind.tvTimer.text=textFormatted(time,"")
        }
        viewModel.maxProgress.observe(this){
            bind.timeProgress.max=it
            bind.timeProgress.progress=it
        }
    }

    private fun setMovementsNum(movements:Int){
        bind.tvMovements.text=textFormatted(movements,getString(R.string.movementsMsg))
    }
    private fun setRemainingPairs(pairs:Int){
        bind.tvRemainingPairs.text=textFormatted(pairs,getString(R.string.pairsMsg))
    }
    private fun checkMovementsPair(){
        if(listOfMovements.size >1) {
            movements++
            setMovementsNum(movements)
           postDelay(300){
                val x = listOfMovements[0].coordinates.first
                val y = listOfMovements[0].coordinates.second
                val x1 = listOfMovements[1].coordinates.first
                val y1 = listOfMovements[1].coordinates.second
                if (viewModel.imagesArray[x][y].description == viewModel.imagesArray[x1][y1].description) {
                    //si hay un par encontrado bloqueamos las vistas para evitar nuevos eventos en ellos
                    getCardView(x, y).isEnabled = false
                    getCardView(x1, y1).isEnabled = false
                    //actualizamos los pares restantes
                    pairs--
                    setRemainingPairs(pairs)

                } else {
                    var count=1L
                    listOfMovements.forEach {
                        val xCord = it.coordinates.first
                        val yCord = it.coordinates.second
                        val imgFront = getCardView(xCord, yCord).findViewById<ImageView>("0".toInt())
                        val imgBack = getCardView(xCord, yCord).findViewById<ImageView>("1".toInt())
                        //desbloqueamos las cards si no son similares para interactuar nuevamente
                        getCardView(xCord,yCord).isEnabled=true
                        //para plicar la animación de flipCard se requiere un retraso para cada cardView
                        postDelay(400*count){viewModel.setAnimation(xCord,yCord,imgFront,imgBack)}
                        count++
                    }
                     }
               listOfMovements.clear()
               if(pairs==0){
                   showGameDialog(R.string.winnerMsg)
               }
        }

        }
    }
    private fun getCardView(x: Int, y: Int): CardView {
        val lnRow = bind.lnContent[x] as LinearLayout
        return lnRow[y] as CardView
    }

}
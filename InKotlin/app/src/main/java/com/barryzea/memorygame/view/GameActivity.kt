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
import com.barryzea.memorygame.common.loadImageRes
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
    private var remainingTime=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpIntentAndViews()
        setUpObservers()
        setUpGameBoard()
    }

    private fun setUpIntentAndViews(){
        intent?.extras?.let{
            COLUMNS=it.getInt(Constants.COLUMN_NUM)
            ROWS=it.getInt(Constants.ROW_NUM)
            when(it.getInt(Constants.LEVEL)){
                Constants.EASY->bind.tvLevel.text=getString(R.string.easyLevel)
                Constants.MEDIUM->bind.tvLevel.text=getString(R.string.mediumLevel)
                Constants.HARD->bind.tvLevel.text=getString(R.string.hardLevel)
            }
        }
        setMovementsNum(0)
        bind.ivGift.loadImageRes(R.drawable.greeter_walk)
        bind.btnBack.setOnClickListener { finish() }
    }
    private fun setUpGameBoard(){
       postDelay(500){viewModel.setUpGameBoard(this,ROWS,COLUMNS,1)}
    }
    private fun setUpObservers(){
        viewModel.viewCreated.observe(this){bind.lnContent.addView(it) }
        viewModel.onCardClicked.observe(this){
            if(pairs>0 && remainingTime>0) {
                val x = it.coordinates.first
                val y = it.coordinates.second
                //cargamos la entidad Card en la lista
                listOfMovements.add(it)

                val imgFront = getCardView(x, y).findViewById<ImageView>("0".toInt())
                val imgBack = getCardView(x, y).findViewById<ImageView>("1".toInt())
                viewModel.setAnimation(x, y, imgFront, imgBack)
                checkMovementsPair()
                //bloqueamos momentaneamente hasta que se haga click en otra vista para comparar
                getCardView(x, y).isEnabled = false
            }
        }
        viewModel.countPairs.observe(this){findPairs->
            setRemainingPairs(findPairs)
            pairs=findPairs
        }
        viewModel.timerCount.observe(this){time-> observeTime(time) }
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
    private fun observeTime(time:Int){
        remainingTime=time
        bind.timeProgress.progress=time
        bind.tvTimer.text=textFormatted(time,"")
        if(time==0 && pairs>0) {//si no se encontraron todos los pares
            bind.ivGift.loadImageRes(R.drawable.greeter4)
            showGameDialog(R.string.retryGameMsg,R.drawable.tim6)}
    }
    private fun checkMovementsPair(){
        if(listOfMovements.size >1) {//si ya tenemos un par en la lista procedemos a comparar
            movements++
            setMovementsNum(movements)
           postDelay(100){
                val x = listOfMovements[0].coordinates.first
                val y = listOfMovements[0].coordinates.second
                val x1 = listOfMovements[1].coordinates.first
                val y1 = listOfMovements[1].coordinates.second
                if (viewModel.imagesArray[x][y].description == viewModel.imagesArray[x1][y1].description) {
                    //si hay un par encontrado bloqueamos las vistas para evitar nuevos eventos en ellas
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
                        //para aplicar la animación de flipCard se requiere un retraso para cada ImageView
                        postDelay(200*count){viewModel.setAnimation(xCord,yCord,imgFront,imgBack)}
                        count++
                    }
                     }
               listOfMovements.clear()//limpiamos la lista para comenzar con otro par
               if(pairs==0){//si ya no quedan pares por encontrar
                   viewModel.stopTimer()
                   bind.ivGift.loadImageRes(R.drawable.greeter4)
                   postDelay(500){showGameDialog(R.string.winnerMsg,R.drawable.tim2)}
               }
        }

        }
    }
    private fun getCardView(x: Int, y: Int): CardView {
        val lnRow = bind.lnContent[x] as LinearLayout
        return lnRow[y] as CardView
    }

}
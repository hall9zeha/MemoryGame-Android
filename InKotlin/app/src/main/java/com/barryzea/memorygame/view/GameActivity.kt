package com.barryzea.memorygame.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.Constants
import com.barryzea.memorygame.common.createSound
import com.barryzea.memorygame.common.getTimeFormatted
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
    private val MINUTES=1

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
        bind.tvMovements.text=textFormatted(0,getString(R.string.movementsMsg))
        bind.ivGift.loadImageRes(R.drawable.greeter_walk)
        bind.btnBack.setOnClickListener { finish() }
    }
    private fun setUpGameBoard(){
       postDelay(500){viewModel.setUpGameBoard(this,ROWS,COLUMNS,MINUTES)}
    }
    private fun setUpObservers(){
        viewModel.viewCreated.observe(this){bind.lnContent.addView(it) }
        viewModel.remainingPairs.observe(this){findPairs->
            setRemainingPairs(findPairs)
        }
        viewModel.movements.observe(this){setMovementsNum(it)}
        viewModel.remainingTime.observe(this){ timeInMillis-> observeTime(timeInMillis) }
        viewModel.maxProgress.observe(this){
            bind.timeProgress.max=it
            bind.timeProgress.progress=it
        }
        viewModel.youWin.observe(this){
            bind.ivGift.loadImageRes(R.drawable.greeter4)
            postDelay(200) {
                if (it) {
                    createSound(this@GameActivity,R.raw.got_piece_easiest)
                    showGameDialog(R.string.winnerMsg, R.drawable.tim2){
                        bind.lnContent.removeAllViews()
                        viewModel.retryGame(this@GameActivity,ROWS,COLUMNS,MINUTES)}
                } else {
                    createSound(this@GameActivity,R.raw.got_piece_hard)
                    showGameDialog(R.string.retryGameMsg, R.drawable.tim6){
                    bind.lnContent.removeAllViews()
                    viewModel.retryGame(this@GameActivity,ROWS,COLUMNS,MINUTES)}
                }
            }
        }
    }
    private fun setMovementsNum(movements:Int){
        bind.tvMovements.text=textFormatted(movements,getString(R.string.movementsMsg))
    }
    private fun setRemainingPairs(pairs:Int){
        bind.tvRemainingPairs.text=textFormatted(pairs,getString(R.string.pairsMsg))
    }
    private fun observeTime(timeInMillis:Long){
        bind.timeProgress.progress=(timeInMillis/1000).toInt()
        bind.tvTimer.text= getTimeFormatted(timeInMillis)

    }

}
package com.barryzea.memorygame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import com.barryzea.memorygame.common.Constants
import com.barryzea.memorygame.databinding.ActivityGameBinding
import com.barryzea.memorygame.viewModel.GameViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGameBinding
    private val viewModel: GameViewModel by viewModels()
    private var COLUMNS=0
    private var ROWS=0
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
    }
    private fun setUpGameBoard(){
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.setUpGameBoard(this,ROWS,COLUMNS)
        },500)
    }
    private fun setUpObservers(){
        viewModel.viewCreated.observe(this){
            bind.lnContent.addView(it)
        }
        viewModel.onCardClicked.observe(this){
            Toast.makeText(this, "${it.coordinates} : ${it.description}" , Toast.LENGTH_SHORT).show()
        }
    }
}
package com.barryzea.memorygame.view


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.barryzea.memorygame.common.Constants
import com.barryzea.memorygame.common.gotoActivity
import com.barryzea.memorygame.databinding.ActivityMainBinding
import com.barryzea.memorygame.viewModel.GameViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding
    private val viewModel:GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpListeners()
    }
   private fun setUpListeners(){
       bind.btnEasy.setOnClickListener { gotoActivity<GameActivity> {
           putExtra(Constants.COLUMN_NUM,Constants.EASY_COLUMNS)
           putExtra(Constants.ROW_NUM,Constants.EASY_ROWS)
       } }
       bind.btnMedium.setOnClickListener {
           gotoActivity<GameActivity> {
               putExtra(Constants.COLUMN_NUM,Constants.MEDIUM_COLUMNS)
               putExtra(Constants.ROW_NUM,Constants.MEDIUM_ROWS)
           }
       }
       bind.btnHard.setOnClickListener {
           gotoActivity<GameActivity> {
               putExtra(Constants.COLUMN_NUM,Constants.HARD_COLUMNS)
               putExtra(Constants.ROW_NUM,Constants.HARD_ROWS)
           }
       }
   }
}
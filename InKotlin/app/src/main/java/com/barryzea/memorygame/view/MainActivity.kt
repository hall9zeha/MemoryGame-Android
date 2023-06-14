package com.barryzea.memorygame.view


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.barryzea.memorygame.databinding.ActivityMainBinding
import com.barryzea.memorygame.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding
    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpObservers()
        setUpGameBoard()
    }
    private fun setUpGameBoard(){
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.setUpGameBoard(this,4,4)
        },500)
    }
    private fun setUpObservers(){
       viewModel.viewCreated.observe(this){
           bind.lnContent.addView(it)
        }
        viewModel.onCardClicked.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}
package com.barryzea.memorygame.viewModel

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.SingleMutableLiveData
import com.barryzea.memorygame.common.createCardView
import com.barryzea.memorygame.common.createLinearLayout

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

class MainViewModel: ViewModel() {

    private var _viewCreated:SingleMutableLiveData<LinearLayout> = SingleMutableLiveData()
    val viewCreated:SingleMutableLiveData<LinearLayout> = _viewCreated
    private var _onCardClicked:SingleMutableLiveData<String> = SingleMutableLiveData()
    val onCardClicked:SingleMutableLiveData<String> = _onCardClicked

    fun setUpGameBoard(ctx:Context, rows:Int,columns:Int){
        //X is row and Y is column
        for(x in 0 until rows){
            val lnRow= createLinearLayout(ctx)
            for(y in 0  until columns){
                val cardView= createCardView(ctx, R.drawable.ic_launcher_background){_onCardClicked.value=it }
                cardView.tag=String.format("%s,%s",x,y)
                lnRow.addView(cardView)
            }
            _viewCreated.value=lnRow
        }
    }
}
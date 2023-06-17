package com.barryzea.memorygame.viewModel

import android.content.Context
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import com.barryzea.memorygame.common.DummyDataSource
import com.barryzea.memorygame.common.SingleMutableLiveData
import com.barryzea.memorygame.common.createCardView
import com.barryzea.memorygame.common.createLinearLayout
import com.barryzea.memorygame.common.entities.Card

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

class GameViewModel: ViewModel() {

    private var _viewCreated:SingleMutableLiveData<LinearLayout> = SingleMutableLiveData()
    val viewCreated:SingleMutableLiveData<LinearLayout> = _viewCreated
    private var _onCardClicked:SingleMutableLiveData<Card> = SingleMutableLiveData()
    val onCardClicked:SingleMutableLiveData<Card> = _onCardClicked

    fun setUpGameBoard(ctx:Context, rows:Int,columns:Int){
        //X is row and Y is column
        val randomList=DummyDataSource.getRandomList(rows*columns)
        for(x in 0 until rows){
            val lnRow= createLinearLayout(ctx)
            for(y in 0  until columns){
                val imageEntity=randomList.random()
                val coordinatesTag=String.format("%s,%s",x,y)
                val cardView= createCardView(ctx,coordinatesTag,imageEntity){ _onCardClicked.value=it}
                lnRow.addView(cardView)
            }
            _viewCreated.value=lnRow
        }
    }
}
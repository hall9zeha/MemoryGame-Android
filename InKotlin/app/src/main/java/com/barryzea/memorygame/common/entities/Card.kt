package com.barryzea.memorygame.common.entities

import androidx.cardview.widget.CardView

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
data class Card(var card:CardView?=null, var status:Boolean=false, var coordinates:Pair<Int,Int> = Pair(0,0), var description:String="")

package com.barryzea.memorygame.common

import android.util.Log
import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.entities.GameImage
import kotlin.random.Random

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 15/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
object DummyDataSource {

    fun getRandomList(boardSize:Int):List<GameImage>{
        val randomList = mutableListOf<GameImage>()

        val imagesList=listOf(
            //GameImage(R.drawable.aleph,"aleph") // no debe estar en la lista ya que es nuesro coverFront para cada Imageview
            GameImage(1,R.drawable.ape,"ape"),
            GameImage(2,R.drawable.cannon1,"cannon1"),
            GameImage(3,R.drawable.cannon2,"cannon2"),
            GameImage(4,R.drawable.claw,"claw"),
            GameImage(5,R.drawable.clawpipe,"clawpipe"),
            GameImage(6,R.drawable.door0,"door0"),
            GameImage(7,R.drawable.door1,"door1"),
            GameImage(8,R.drawable.door2,"door2"),
            GameImage(9,R.drawable.door3,"door3"),
            GameImage(10,R.drawable.door4,"door4"),
            GameImage(11,R.drawable.door5,"door5"),
            GameImage(12,R.drawable.door6,"door6"),
            GameImage(13,R.drawable.flora1,"flora1"),
            GameImage(14,R.drawable.flora2,"flora2"),
            GameImage(15,R.drawable.flora3,"flora3"),
            GameImage(16,R.drawable.flower,"flower"),
            GameImage(17,R.drawable.greeter1,"greeter1"),
            GameImage(18,R.drawable.greeter2,"greeter2"),
            GameImage(19,R.drawable.greeter3,"greeter3"),
            GameImage(20,R.drawable.greeter4,"greeter4"),
            GameImage(21,R.drawable.key1,"key1"),
            GameImage(22,R.drawable.key2,"key2"),
            GameImage(23,R.drawable.mimic1,"mimic1"),
            GameImage(24,R.drawable.mimic2,"mimic2"),
            GameImage(25,R.drawable.mimic3,"mimic3"),
            GameImage(26,R.drawable.mimic5,"mimic5"),
            GameImage(27,R.drawable.monster1,"monster1"),
            GameImage(28,R.drawable.stevinus,"stevinus"),
            GameImage(29,R.drawable.switch2,"switch2"),
            GameImage(30,R.drawable.tim0,"tim0"),
            GameImage(31,R.drawable.tim1,"tim1"),
            GameImage(32,R.drawable.tim3,"tim3"),
            GameImage(33,R.drawable.tim8,"tim8"),
        )
        for(i in 0 until boardSize){
            randomList.add(imagesList[Random.nextInt(0,imagesList.size-1)])
        }
        return randomList
    }

}
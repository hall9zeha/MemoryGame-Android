package com.barryzea.memorygame.common

import com.barryzea.memorygame.R
import com.barryzea.memorygame.common.entities.ImageGame
import kotlin.random.Random

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 15/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
object DummyDataSource {

    fun getRandomList(boardSize:Int):List<ImageGame>{
        val randomList = mutableListOf<ImageGame>()

        val imagesList=listOf(
            //GameImage(R.drawable.aleph,"aleph") // no debe estar en la lista ya que es nuesro coverFront para cada Imageview
            ImageGame(1,R.drawable.ape,"ape"),
            ImageGame(2,R.drawable.cannon1,"cannon1"),
            ImageGame(3,R.drawable.cannon2,"cannon2"),
            ImageGame(4,R.drawable.claw,"claw"),
            ImageGame(5,R.drawable.clawpipe,"clawpipe"),
            ImageGame(6,R.drawable.door0,"door0"),
            ImageGame(7,R.drawable.door1,"door1"),
            ImageGame(8,R.drawable.door2,"door2"),
            ImageGame(9,R.drawable.door3,"door3"),
            ImageGame(10,R.drawable.door4,"door4"),
            ImageGame(11,R.drawable.door5,"door5"),
            ImageGame(12,R.drawable.door6,"door6"),
            ImageGame(13,R.drawable.flora1,"flora1"),
            ImageGame(14,R.drawable.flora2,"flora2"),
            ImageGame(15,R.drawable.flora3,"flora3"),
            ImageGame(16,R.drawable.flower,"flower"),
            ImageGame(17,R.drawable.greeter1,"greeter1"),
            ImageGame(18,R.drawable.greeter2,"greeter2"),
            ImageGame(19,R.drawable.greeter3,"greeter3"),
            ImageGame(20,R.drawable.greeter4,"greeter4"),
            ImageGame(21,R.drawable.key1,"key1"),
            ImageGame(22,R.drawable.key2,"key2"),
            ImageGame(23,R.drawable.mimic1,"mimic1"),
            ImageGame(24,R.drawable.mimic2,"mimic2"),
            ImageGame(25,R.drawable.mimic3,"mimic3"),
            ImageGame(26,R.drawable.mimic5,"mimic5"),
            ImageGame(27,R.drawable.monster1,"monster1"),
            ImageGame(28,R.drawable.stevinus,"stevinus"),
            ImageGame(29,R.drawable.switch2,"switch2"),
            ImageGame(30,R.drawable.tim0,"tim0"),
            ImageGame(31,R.drawable.tim1,"tim1"),
            ImageGame(32,R.drawable.tim3,"tim3"),
            ImageGame(33,R.drawable.tim8,"tim8"),
        )
        for(i in 0 until boardSize){
            randomList.add(imagesList[Random.nextInt(0,imagesList.size-1)])
        }
        return randomList
    }

}
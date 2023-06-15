package com.barryzea.memorygame.common

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

    fun getRandomList(range:Int):List<GameImage>{
        val randomList = mutableListOf<GameImage>()

        val imagesList=listOf(
            GameImage(R.drawable.aleph,"aleph"),
            GameImage(R.drawable.ape,"ape"),
            GameImage(R.drawable.cannon1,"cannon1"),
            GameImage(R.drawable.cannon2,"cannon2"),
            GameImage(R.drawable.claw,"claw"),
            GameImage(R.drawable.clawpipe,"clawpipe"),
            GameImage(R.drawable.door0,"door0"),
            GameImage(R.drawable.door1,"door1"),
            GameImage(R.drawable.door2,"door2"),
            GameImage(R.drawable.door3,"door3"),
            GameImage(R.drawable.door4,"door4"),
            GameImage(R.drawable.door5,"door5"),
            GameImage(R.drawable.door6,"door6"),
            GameImage(R.drawable.flora1,"flora1"),
            GameImage(R.drawable.flora2,"flora2"),
            GameImage(R.drawable.flora3,"flora3"),
            GameImage(R.drawable.flower,"flower"),
            GameImage(R.drawable.greeter1,"greeter1"),
            GameImage(R.drawable.greeter2,"greeter2"),
            GameImage(R.drawable.greeter3,"greeter3"),
            GameImage(R.drawable.greeter4,"greeter4"),
            GameImage(R.drawable.key1,"key1"),
            GameImage(R.drawable.key2,"key2"),
            GameImage(R.drawable.mimic1,"mimic1"),
            GameImage(R.drawable.mimic2,"mimic2"),
            GameImage(R.drawable.mimic3,"mimic3"),
            GameImage(R.drawable.mimic5,"mimic5"),
            GameImage(R.drawable.monster1,"monster1"),
            GameImage(R.drawable.stevinus,"stevinus"),
            GameImage(R.drawable.switch2,"switch2"),
            GameImage(R.drawable.tim0,"tim0"),
            GameImage(R.drawable.tim1,"tim1"),
            GameImage(R.drawable.tim3,"tim3"),
            GameImage(R.drawable.tim8,"tim8"),
        )
        for(i in 0 until range){
            randomList.add(imagesList[Random.nextInt(0,range)])
        }
        return randomList
    }

}
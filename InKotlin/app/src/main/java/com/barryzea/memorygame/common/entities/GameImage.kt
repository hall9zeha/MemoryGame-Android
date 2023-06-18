package com.barryzea.memorygame.common.entities

import android.graphics.drawable.Drawable

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 15/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
data class GameImage(var id:Int,var imageRes: Int=0, var name:String="") {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameImage

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}

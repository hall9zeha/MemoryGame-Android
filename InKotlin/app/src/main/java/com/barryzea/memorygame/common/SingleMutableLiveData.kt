package com.barryzea.memorygame.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Project MemoryGame
 * Created by Barry Zea H. on 14/06/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
class SingleMutableLiveData<T>() : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer{t->
            if(t != null){
                observer.onChanged(t)
                postValue(null)
            }
        })
    }
    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }
}
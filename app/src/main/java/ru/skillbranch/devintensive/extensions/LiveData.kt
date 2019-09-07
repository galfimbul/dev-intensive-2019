package ru.skillbranch.devintensive.extensions

import androidx.lifecycle.MutableLiveData

/**
 * Created by Alexander Shvetsov on 30.08.2019
 */

fun <T> mutableLiveData(defaultValue: T? = null) : MutableLiveData<T>{
    val data = MutableLiveData<T>()

    if(defaultValue!=null){
        data.value = defaultValue
    }
    return data
}
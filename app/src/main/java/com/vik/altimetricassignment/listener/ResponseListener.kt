package com.vik.altimetricassignment.listener

interface ResponseListener<T> {
    fun onSuccess(t : T)
    fun onError(error : String)
}
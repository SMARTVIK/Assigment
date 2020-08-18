package com.vik.altimetricassignment.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vik.altimetricassignment.repository.MainRepository

class MainViewModelFactory(val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repo = MainRepository(context)
       return  modelClass.getConstructor(MainRepository::class.java).newInstance(repo)
    }
}
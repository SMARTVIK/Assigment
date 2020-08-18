package com.vik.altimetricassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vik.altimetricassignment.listener.ResponseListener
import com.vik.altimetricassignment.model.Resource
import com.vik.altimetricassignment.model.SearchModel
import com.vik.altimetricassignment.repository.MainRepository

class MainActivityViewModel(var mainRepo : MainRepository) : ViewModel(){

    var searchData : MutableLiveData<Resource<SearchModel>> = MutableLiveData()


    fun getSearchData() : LiveData<Resource<SearchModel>> {
        return searchData
    }

    fun searchTerm(term : String ) {
        searchData.postValue(Resource.loading(null))

        mainRepo.searchItem(term, object : ResponseListener<SearchModel>{
            override fun onSuccess(t: SearchModel) {
                searchData.postValue(Resource.success(t))
            }

            override fun onError(error: String) {
                searchData.postValue(Resource.error(error,null))
            }
        })


    }

}
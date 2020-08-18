package com.vik.altimetricassignment.repository

import android.content.Context
import com.vik.altimetricassignment.listener.ResponseListener
import com.vik.altimetricassignment.model.SearchModel
import com.vik.altimetricassignment.network.ApiClient
import com.vik.altimetricassignment.network.Const
import com.vik.altimetricassignment.network.WebApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(val context : Context) {

    fun searchItem(term: String, responseListener: ResponseListener<SearchModel>) {
        val call = ApiClient().getClient()!!.create(WebApi::class.java)
        call.searchItem(term)
            .enqueue(object : Callback<SearchModel> {
                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                    responseListener.onError(t.message.toString())
                }

                override fun onResponse(
                    call: Call<SearchModel>,
                    response: Response<SearchModel>
                ) {
                    if (response.code() == 200) {
                        response.body()?.let { responseListener.onSuccess(it) }
                    } else {
                        responseListener.onError(response.message())
                    }
                }
            })
    }

}
package com.vik.altimetricassignment.network

import com.vik.altimetricassignment.model.SearchModel
import com.vik.altimetricassignment.network.Const.URLS.SEARCH
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WebApi {

    @GET(SEARCH)
    fun searchItem(@Query("term") term: String
    ): Call<SearchModel>
}
package com.github.abhi10jul.endlesslistscrolling_kotlinexample

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  @author abhishek srivastava.
 */
class ApiClient {
    private val BASE_URL = "WRITE YOUR BASE URL"
    private var apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun callTvShowListRequest(page: Int, callback: Callback<ShowModel>) {
        apiService.getTvShowList(page).enqueue(callback)
    }
}
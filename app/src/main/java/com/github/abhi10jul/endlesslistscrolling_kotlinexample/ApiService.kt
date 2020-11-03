package com.github.abhi10jul.endlesslistscrolling_kotlinexample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  @author abhishek srivastava.
 */
interface ApiService {

    @GET("{{WRITE YOUR END POINT}}")
    fun getTvShowList(@Query("page") page: Int): Call<ShowModel>

}
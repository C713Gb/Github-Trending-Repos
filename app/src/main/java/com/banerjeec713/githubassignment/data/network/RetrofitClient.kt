package com.banerjeec713.githubassignment.data.network

import com.banerjeec713.githubassignment.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun create(): RetrofitApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

        return retrofit.create(RetrofitApi::class.java)
    }

}
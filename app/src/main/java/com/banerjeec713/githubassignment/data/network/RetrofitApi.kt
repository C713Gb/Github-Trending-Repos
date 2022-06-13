package com.banerjeec713.githubassignment.data.network

import com.banerjeec713.githubassignment.data.models.TrendingItemModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitApi {

    @GET("/repositories")
    fun getTrendingRepos(): Observable<List<TrendingItemModel>>

}
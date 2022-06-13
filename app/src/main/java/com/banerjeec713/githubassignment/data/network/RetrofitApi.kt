package com.banerjeec713.githubassignment.data.network

import com.banerjeec713.githubassignment.data.models.RepoModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitApi {

    @GET("/search/repositories")
    fun getRepos(@QueryMap filter: Map<String, String>): Observable<RepoModel>

}
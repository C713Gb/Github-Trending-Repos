package com.banerjeec713.githubassignment.data

import com.banerjeec713.githubassignment.data.models.TrendingItemModel
import com.banerjeec713.githubassignment.data.network.RetrofitClient.create
import io.reactivex.Observable

class RetrofitManager private constructor() {
    fun getTrendingRepos(): Observable<List<TrendingItemModel>>{
        val api = create()
        return api.getTrendingRepos()
    }

    companion object {
        private var mInstance: RetrofitManager? = null
        val instance: RetrofitManager
            get() = if (mInstance == null) RetrofitManager().also {
                mInstance = it
            } else mInstance!!
    }
}
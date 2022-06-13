package com.banerjeec713.githubassignment.data

import android.content.Context
import com.banerjeec713.githubassignment.data.RetrofitManager.Companion.instance
import com.banerjeec713.githubassignment.data.models.TrendingItemModel
import io.reactivex.Observable

class DataManager private constructor(context: Context) {
    private val retrofitManager: RetrofitManager = instance

    fun getTrendingRepos(): Observable<List<TrendingItemModel>>{
        return retrofitManager.getTrendingRepos()
    }

    companion object {
        private var mInstance: DataManager? = null
        @Synchronized
        fun getInstance(context: Context): DataManager {
            return if (mInstance == null) DataManager(context).also {
                mInstance = it
            } else mInstance!!
        }
    }

}
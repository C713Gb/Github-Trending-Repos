package com.banerjeec713.githubassignment.data

import com.banerjeec713.githubassignment.data.models.RepoModel
import com.banerjeec713.githubassignment.data.network.RetrofitClient.create
import io.reactivex.Observable

class RetrofitManager private constructor() {
    fun getRepos(map: Map<String, String>): Observable<RepoModel> {
        val api = create()
        return api.getRepos(map)
    }

    companion object {
        private var mInstance: RetrofitManager? = null
        val instance: RetrofitManager
            get() = if (mInstance == null) RetrofitManager().also {
                mInstance = it
            } else mInstance!!
    }
}
package com.banerjeec713.githubassignment.ui.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.banerjeec713.githubassignment.data.DataManager
import com.banerjeec713.githubassignment.data.models.TrendingItemModel
import com.banerjeec713.githubassignment.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel internal constructor(private val dataManager: DataManager) : BaseViewModel() {
    val repos = MutableLiveData<List<TrendingItemModel>>()
    val error = MutableLiveData<Boolean>()
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun loadRepos() {
        disposable.add(
            dataManager.getTrendingRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ itemModels: List<TrendingItemModel>? ->
                    if (itemModels != null && !itemModels.isEmpty()) {
                        repos.postValue(itemModels)
                        error.postValue(false)
                    }
                }
                ) { this.error.postValue(true) }
        )
    }

    fun onClear() {
        disposable.dispose()
    }

    private lateinit var state: Parcelable
    fun saveRecyclerViewState(parcelable: Parcelable) { state = parcelable }
    fun restoreRecyclerViewState() : Parcelable = state
    fun stateInitialized() : Boolean = ::state.isInitialized
}
package com.banerjeec713.githubassignment.ui.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.banerjeec713.githubassignment.data.DataManager
import com.banerjeec713.githubassignment.data.models.ItemModel
import com.banerjeec713.githubassignment.data.models.RepoModel
import com.banerjeec713.githubassignment.ui.base.BaseViewModel
import com.banerjeec713.githubassignment.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel internal constructor(private val dataManager: DataManager) : BaseViewModel() {
    val repos = MutableLiveData<List<ItemModel>>()
    val error = MutableLiveData<Boolean>()
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun loadRepos(date: String?) {
        initMap()
        if (date != null && !date.isEmpty()) map["q"] = "created:>$date"
        disposable.add(
            dataManager.getRepos(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(RepoModel::items)
                .subscribe({ itemModels: List<ItemModel>? ->
                    if (itemModels != null && !itemModels.isEmpty()) {
                        repos.postValue(itemModels)
                        error.postValue(false)
                    }
                }
                ) { error: Throwable? -> this.error.postValue(true) }
        )
    }

    private fun initMap() {
        map["q"] = "created:>"
        map["sort"] = "stars"
        map["order"] = "desc"
        map["page"] =
            java.lang.String.valueOf(Constants.PAGE_COUNT)
    }

    fun onClear() {
        disposable.dispose()
    }

    companion object {
        private val map: MutableMap<String, String> = HashMap()
    }

    private lateinit var state: Parcelable
    fun saveRecyclerViewState(parcelable: Parcelable) { state = parcelable }
    fun restoreRecyclerViewState() : Parcelable = state
    fun stateInitialized() : Boolean = ::state.isInitialized
}
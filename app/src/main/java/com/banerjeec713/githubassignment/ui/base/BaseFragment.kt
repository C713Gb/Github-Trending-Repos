package com.banerjeec713.githubassignment.ui.base

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {
    protected var fViewModel: T? = null
    abstract fun getViewModel(): T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fViewModel = getViewModel()
    }

    override fun onDetach() {
        super.onDetach()
    }
}
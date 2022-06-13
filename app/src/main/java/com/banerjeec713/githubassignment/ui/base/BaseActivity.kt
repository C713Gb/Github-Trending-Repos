package com.banerjeec713.githubassignment.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
    protected var viewModel: T? = null
    abstract fun createViewModel(): T
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        viewModel = createViewModel()
    }
}
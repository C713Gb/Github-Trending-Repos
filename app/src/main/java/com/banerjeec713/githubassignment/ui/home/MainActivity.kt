package com.banerjeec713.githubassignment.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.banerjeec713.githubassignment.App
import com.banerjeec713.githubassignment.R
import com.banerjeec713.githubassignment.data.DataManager
import com.banerjeec713.githubassignment.ui.base.BaseActivity
import com.banerjeec713.githubassignment.utils.Util

class MainActivity : BaseActivity<MainViewModel>() {

    @BindView(R.id.main_layout)
    private lateinit var mainLayout: LinearLayout

    @BindView(R.id.imgview)
    private lateinit var imageView: ImageView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        if (App.instance?.let { Util.isNetworkAvailable(it) } == true){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.getInstance())
                .commit()
        } else {
            Util.showSnack(mainLayout,true,"No Internet Connection! ");
            imageView.visibility = View.VISIBLE;
        }
    }

    override fun createViewModel(): MainViewModel {
        val factory = MainViewModelFactory(DataManager.getInstance(App.instance!!))
        return ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
}
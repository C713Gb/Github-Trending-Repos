package com.banerjeec713.githubassignment.ui.home

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.banerjeec713.githubassignment.App
import com.banerjeec713.githubassignment.R
import com.banerjeec713.githubassignment.data.DataManager
import com.banerjeec713.githubassignment.ui.base.BaseActivity
import com.banerjeec713.githubassignment.utils.Util

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var mainLayout: LinearLayout
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.main_layout)
        imageView = findViewById(R.id.imgview)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.actionSearch)
        val searchView = searchItem?.actionView as SearchView
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) != null) {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as MainFragment
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null && !p0.isEmpty())
                        fragment.filter(p0)
                    else
                        fragment.reset()
                    return false
                }

            })
        }
        return true
    }
}
package com.banerjeec713.githubassignment

import android.app.Application
import com.banerjeec713.githubassignment.App

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: App? = null
            private set
    }
}
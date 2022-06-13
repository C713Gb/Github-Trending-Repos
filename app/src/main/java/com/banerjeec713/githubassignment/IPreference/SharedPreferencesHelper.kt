package com.banerjeec713.githubassignment.IPreference

import android.content.Context
import android.content.SharedPreferences
import com.banerjeec713.githubassignment.IPreference.SharedPreferencesHelper
import kotlin.jvm.Synchronized

class SharedPreferencesHelper private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences
    var date: String?
        get() = sharedPreferences.getString(DATE, null)
        set(date) {
            sharedPreferences.edit()
                .putString(DATE, date)
                .apply()
        }

    companion object {
        private const val DATE = "date"
        private var mInstance: SharedPreferencesHelper? = null
        @Synchronized
        fun getInstance(context: Context): SharedPreferencesHelper {
            return if (mInstance == null) SharedPreferencesHelper(context).also {
                mInstance = it
            } else mInstance!!
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}
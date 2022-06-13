package com.banerjeec713.githubassignment.utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.RequiresApi
import android.os.Build
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import com.banerjeec713.githubassignment.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun getYear(mDate: String?): Int {
        var date: Date? = null
        try {
            date = SimpleDateFormat("yyyy-MM-dd").parse(mDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return SimpleDateFormat("yyyy").format(date).toInt()
    }

    fun getMonth(mDate: String?): Int {
        var date: Date? = null
        try {
            date = SimpleDateFormat("yyyy-MM-dd").parse(mDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return SimpleDateFormat("MM").format(date).toInt()
    }

    fun getDay(mDate: String?): Int {
        var date: Date? = null
        try {
            date = SimpleDateFormat("yyyy-MM-dd").parse(mDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return SimpleDateFormat("dd").format(date).toInt()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun isNetworkAvailable(context: Context): Boolean {
        val mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Test for connection
        val mCapabilities =
            mConnectivityManager.getNetworkCapabilities(mConnectivityManager.activeNetwork)
        return mCapabilities != null &&
                (mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    // Showing the status in Snackbar
    fun showSnack(view: View?, isError: Boolean, message: String?) {
        var color = Color.WHITE
        if (isError) color = Color.RED
        val snackbar = Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(color)
        snackbar.show()
    }

    fun formatDate(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.DAY_OF_MONTH] = day
        cal[Calendar.MONTH] = month - 1
        return SimpleDateFormat("yyyy-MM-dd").format(cal.time)
    }

    val defaultDate: String
        get() = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000))
}
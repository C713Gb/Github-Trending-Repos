package com.banerjeec713.githubassignment.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.banerjeec713.githubassignment.R
import com.banerjeec713.githubassignment.data.models.ItemModel
import com.bumptech.glide.Glide
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(mViewHolder: ViewHolder, position: Int) {

        if (selectedPosition == position)
            mViewHolder.itemView.setBackgroundColor(Color.parseColor("#000000"))
        else
            mViewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

        val data = mData!![position]
        val mCount =
            if (data.star_count >= 1000) (data.star_count / 1000).toString() + "k" else data.star_count.toString()
        if (data.name != null) mViewHolder.setTitle(data.name)
        if (data.description != null) mViewHolder.setDescription(data.description)
        if (data.owners != null) mViewHolder.setAvatar(data.owners.avatar_url)
        if (data.language != null) mViewHolder.setLanguage(data.language)
        if (data.licenses != null) mViewHolder.setLicense(data.licenses.name)
        mViewHolder.setStarCount(mCount)

        mViewHolder.itemView.setOnClickListener {
            selectedPosition = mViewHolder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val mTitle: TextView
        private val mDescription: TextView
        private val mStar: TextView
        private val mLang: TextView
        private val mAvatatImg: ImageView
        private val mLicenseView: TextView
        fun setTitle(title: String?) {
            mTitle.text = title
        }

        fun setDescription(description: String?) {
            mDescription.text = description
        }

        fun setStarCount(count: String?) {
            mStar.text = count
        }

        fun setLanguage(language: String?) {
            mLang.text = language
        }

        fun setAvatar(avatar: String?) {
            Glide.with(itemView.context)
                .load(avatar)
                .into(mAvatatImg)
        }

        fun setLicense(license: String?) {
            mLicenseView.text = license
        }

        init {
            mView.setOnClickListener { view: View -> copyToClip(view.context, adapterPosition) }
            mTitle = mView.findViewById(R.id.title)
            mDescription = mView.findViewById(R.id.description)
            mAvatatImg = mView.findViewById(R.id.avatarImg)
            mStar = mView.findViewById(R.id.starView)
            mLang = mView.findViewById(R.id.langView)
            mLicenseView = mView.findViewById(R.id.licenseView)
        }
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }

    fun addData(mData: List<ItemModel>?) {
        Companion.mData!!.addAll(mData!!)
        notifyDataSetChanged()
    }

    companion object {
        private var mData: MutableList<ItemModel>? = ArrayList()
        private var selectedPosition = -1
        fun copyToClip(context: Context, position: Int) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Git URL", mData!![position].clone_url)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Link Copied to the Clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}
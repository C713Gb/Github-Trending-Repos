package com.banerjeec713.githubassignment.ui.home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.banerjeec713.githubassignment.R
import com.banerjeec713.githubassignment.data.models.TrendingItemModel
import com.banerjeec713.githubassignment.utils.Constants.TAG
import com.bumptech.glide.Glide
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(mViewHolder: ViewHolder, position: Int) {

        mViewHolder.itemView.setOnClickListener {
            selectedPosition = mViewHolder.adapterPosition
            notifyDataSetChanged()
        }

        mViewHolder.bind(selectedPosition, mData!![position])

    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val mTitle: TextView
        private val mDescription: TextView
        private val mAvatarImg: ImageView
        private val mCard: CardView

        fun setTitle(title: String?) {
            mTitle.text = title
        }

        fun setDescription(description: String?) {
            mDescription.text = description
        }

        fun setAvatar(avatar: String?) {
            Glide.with(itemView.context)
                .load(avatar)
                .into(mAvatarImg)
        }

        fun bind(selectedPosition: Int, itemModel: TrendingItemModel) {
            val data = itemModel
            setTitle(data.name)
            setDescription(data.description)
            setAvatar(data.avatar)

            if (selectedPosition == adapterPosition) {
                Log.d(TAG, "onBindViewHolder: $selectedPosition")
                mCard.setCardBackgroundColor(Color.parseColor("#03DAC5"))
            }
            else{
                mCard.setCardBackgroundColor(Color.parseColor("#ffffff"))
            }
        }

        init {
            mTitle = mView.findViewById(R.id.title)
            mDescription = mView.findViewById(R.id.description)
            mAvatarImg = mView.findViewById(R.id.avatarImg)
            mCard = mView.findViewById(R.id.cardView)
        }
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }

    fun addData(mData: List<TrendingItemModel>?) {
        Companion.mData!!.addAll(mData!!)
        notifyDataSetChanged()
    }

    companion object {
        private var mData: MutableList<TrendingItemModel>? = ArrayList()
        private var selectedPosition = -1
    }
}
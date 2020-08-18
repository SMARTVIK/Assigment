package com.vik.altimetricassignment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vik.altimetricassignment.R
import com.vik.altimetricassignment.listener.OnItemClick
import com.vik.altimetricassignment.model.Result
import kotlinx.android.synthetic.main.album_item.view.*
import kotlin.collections.ArrayList


class SearchItemAdapter(
    val mContext: Context,
    var onItemClick : OnItemClick<Result>
) : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>(){

    private var filteredList: List<Result>? = ArrayList()
    var results: List<Result> ? = ArrayList()

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

         var ivPreviewImage : ImageView ? = null
         var tvCollectionCensoredName : TextView ? = null
         var tvCollectionPrice : TextView ? = null
         var tvReleaseDate : TextView ? = null

        init {
            ivPreviewImage = itemView.ivPreviewImage
            tvCollectionCensoredName = itemView.tvCollectionCensoredName
            tvCollectionPrice = itemView.tvCollectionPrice
            tvReleaseDate = itemView.tvReleaseDate
            itemView.setOnClickListener { results?.get(layoutPosition)?.let { it1 ->
                onItemClick.onItemClick(
                    it1
                )
            } }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false))
    }

    override fun getItemCount(): Int {
        return filteredList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results!!.get(position)
        Glide.with(mContext).load(result.previewUrl).placeholder(R.drawable.ic_placeholder).into(holder.ivPreviewImage!!)
        holder.tvCollectionCensoredName?.setText(result.collectionCensoredName)
        holder.tvCollectionPrice?.setText("$${result.collectionPrice}")
        holder.tvReleaseDate?.setText("${result.releaseDate}")
    }

    fun setData(results: List<Result>?) {
        this.results = results
        filteredList = results
        notifyDataSetChanged()
    }

}
package com.example.vama.ui.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vama.R
import com.example.vama.domain.model.Album
import com.example.vama.utils.AlbumsItemDiffCallback

class AlbumsAdapter(
    private val albumList: MutableList<Album>,
    private val selected: (position: Int) -> Unit
) : RecyclerView.Adapter<AlbumsAdapter.AlbumsHW>() {

    inner class AlbumsHW(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMenu(item: Album) {
            itemView.findViewById<TextView>(R.id.tx_label)
                .text = item.author

            itemView.findViewById<TextView>(R.id.tx_title)
                .text = item.title

            Glide.with(itemView.context)
                .load(item.imageURL)
                .centerCrop()
                .placeholder(ColorDrawable(Color.BLACK))
                .into(itemView.findViewById(R.id.im_album))

            itemView.setOnClickListener { selected(adapterPosition) }
        }

    }

    override fun onBindViewHolder(holder: AlbumsHW, position: Int) {
        holder.bindMenu(albumList[position])
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    fun updateData(value: List<Album>) {
        val diffCallback = AlbumsItemDiffCallback(albumList, value)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        albumList.clear()
        albumList.addAll(value)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHW {
        return AlbumsHW(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.albums_item, parent, false)
        )
    }
}
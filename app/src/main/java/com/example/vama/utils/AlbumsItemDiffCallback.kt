package com.example.vama.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.vama.domain.model.Album

class AlbumsItemDiffCallback(
    private var mOldAlbum: List<Album>? = null,
    private var mNewAlbum: List<Album>? = null
) : DiffUtil.Callback() {

    fun contactDiffCallback(
        oldEmployeeList: List<Album>?,
        newEmployeeList: List<Album>?
    ) {
        mOldAlbum = oldEmployeeList
        mNewAlbum = newEmployeeList
    }

    override fun getOldListSize(): Int {
        return mOldAlbum!!.size
    }

    override fun getNewListSize(): Int {
        return mNewAlbum!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldAlbum!![oldItemPosition].title == mNewAlbum!![newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact: Album = mOldAlbum!![oldItemPosition]
        val newContact: Album = mNewAlbum!![newItemPosition]
        return oldContact.title == newContact.title
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
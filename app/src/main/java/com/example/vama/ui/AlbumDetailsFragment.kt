package com.example.vama.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.vama.R
import com.example.vama.domain.model.Album


class AlbumDetailsFragment(
    //Or with a bundle
    private val album: Album
) : Fragment() {

    companion object {
        fun newInstance(album: Album) = AlbumDetailsFragment(album)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_album_details, container, false)

        Glide.with(this)
            .load(album.imageURL)
            .centerCrop()
            .into(rootView.findViewById(R.id.im_album_detail))

        rootView.findViewById<TextView>(R.id.tx_album_title_detail)
            .text = album.title

        rootView.findViewById<TextView>(R.id.tx_author)
            .text = album.author

//        rootView.findViewById<TextView>(R.id.tx_label)
//            .text = album.label

        rootView.findViewById<TextView>(R.id.tx_released)
            .text = album.released

        rootView.findViewById<ImageView>(R.id.im_back)
            .setOnClickListener {
                requireActivity().onBackPressed()
            }
        rootView.findViewById<Button>(R.id.bt_open_album)
            .setOnClickListener {
                try {
                    val defaultBrowser = Intent.makeMainSelectorActivity(
                        Intent.ACTION_MAIN,
                        Intent.CATEGORY_APP_BROWSER
                    )

                    defaultBrowser.data = Uri.parse(album.albumURL)
                    startActivity(defaultBrowser)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        return rootView
    }
}
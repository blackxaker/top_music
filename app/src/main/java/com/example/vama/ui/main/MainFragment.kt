package com.example.vama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.vama.R
import com.example.vama.ui.AlbumDetailsFragment
import com.example.vama.ui.adapters.AlbumsAdapter

class MainFragment(
    private val showAlbum: (albumDetailsFragment: AlbumDetailsFragment) -> Unit
) : Fragment() {

    companion object {
        fun newInstance(showAlbum: (albumDetailsFragment: AlbumDetailsFragment) -> Unit) =
            MainFragment(showAlbum)
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: AlbumsAdapter by lazy {
        AlbumsAdapter(mutableListOf()) { position ->
            viewModel.albums.value?.let {
                showAlbum(AlbumDetailsFragment.newInstance(it[position]))
            }
        }
    }
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        rv = rootView.findViewById(R.id.rv_albums)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.adapter = adapter

        viewModel.albums.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }
}
package com.example.vama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
            viewModel.onClickAlbum(position)
        }
    }
    private lateinit var rv: RecyclerView
    private lateinit var loadingView: View
    private lateinit var loadingProgress: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var emptyListLabel: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        rv = rootView.findViewById(R.id.rv_albums)
        emptyListLabel = rootView.findViewById(R.id.tx_empty_list)
        loadingView = rootView.findViewById(R.id.v_loading)
        loadingProgress = rootView.findViewById(R.id.pb_loading)
        refreshLayout = rootView.findViewById(R.id.swiperefresh)
        refreshLayout .setOnRefreshListener {
                viewModel.onRefresh()
            }

        loadingView.setOnClickListener {

        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.adapter = adapter

        viewModel.subscribeOnState(viewLifecycleOwner) {
            when (it) {
                is StateMain.ShowAlbums -> {
                    refreshLayout.isRefreshing = false

                    if(it.albums.isNotEmpty()){
                        emptyListLabel.visibility = View.INVISIBLE
                    }

                    adapter.updateData(it.albums)
                }
                StateMain.ShowConnectionError -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.connection_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                StateMain.ShowGeneralError -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.general_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                StateMain.ShowLoading -> visibilityLoading(true)
                StateMain.ShowServerError -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.server_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                StateMain.Wait -> {}
                is StateMain.ShowAlbumDetails -> {
                    showAlbum(AlbumDetailsFragment.newInstance(it.album))
                }
                StateMain.HideLoading -> visibilityLoading(false)
            }
        }
    }

    private fun visibilityLoading(isVisible: Boolean) {
        loadingProgress.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        loadingView.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}
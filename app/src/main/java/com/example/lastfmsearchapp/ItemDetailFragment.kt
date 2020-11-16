package com.example.lastfmsearchapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lastfmsearchapp.viewmodel.InitialSearchViewModel

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private lateinit var initialSearchViewModel: InitialSearchViewModel // TODO rename the varname, change the var name to something more sensible

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialSearchViewModel = ViewModelProvider(this).get(InitialSearchViewModel::class.java)

        arguments?.let {
            if (it.containsKey(ARG_ARTIST_NAME)) {
                initialSearchViewModel.fetchArtistInformation(it.getString(ARG_ARTIST_NAME)!!) // TODO review the !!
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = "Artist : ${it.getString(ARG_ARTIST_NAME)!!}"
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the content as text in a TextView.
        val artistInfoSearchResultObserver = Observer<MutableMap<String, String>> {
            rootView.findViewById<TextView>(R.id.item_detail).text = it["artistbiosummary"]
        }

        initialSearchViewModel.artistInfoSearchResponseReceived.observe(viewLifecycleOwner, artistInfoSearchResultObserver)

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the artist name that this fragment
         * represents.
         */
        const val ARG_ARTIST_NAME = "artistname"
    }
}
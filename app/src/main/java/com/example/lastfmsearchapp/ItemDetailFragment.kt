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
import com.example.lastfmsearchapp.dummy.DummyContent
import com.example.lastfmsearchapp.viewmodel.InitialSearchViewModel

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: DummyContent.DummyItem? = null

    private var artistName: String = ""

    private lateinit var initialSearchViewModel: InitialSearchViewModel // TODO rename the varname, change the var name to something more sensible


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialSearchViewModel = ViewModelProvider(this).get(InitialSearchViewModel::class.java)


        arguments?.let {
            if (it.containsKey(ARG_ARTIST_NAME)) {
                //item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]

                initialSearchViewModel.fetchArtistInformation(it.getString(ARG_ARTIST_NAME)!!) // TODO review the !!

                //val searchResultObserver = Observer

                //activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = item?.content

            }
        }

        /*arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = item?.content
            }
        }*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        /*item?.let {
            rootView.findViewById<TextView>(R.id.item_detail).text = it.details
        }*/

        val artistInfoSearchResultObserver = Observer<MutableMap<String, String>> {
            rootView.findViewById<TextView>(R.id.item_detail).text = it["artistbiosummary"]
        }

        initialSearchViewModel.artistInfoSearchResponseReceived.observe(viewLifecycleOwner, artistInfoSearchResultObserver)

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        //const val ARG_ITEM_ID = "item_id" // TODO to be removed.
        const val ARG_ARTIST_NAME = "artistname"
    }
}
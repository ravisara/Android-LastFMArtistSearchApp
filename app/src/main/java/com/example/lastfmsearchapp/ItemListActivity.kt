package com.example.lastfmsearchapp

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.lastfmsearchapp.viewmodel.SearchArtistOnLastFMViewModel

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    private lateinit var searchArtistOnLastFMViewModel: SearchArtistOnLastFMViewModel
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var okToContinue = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        searchArtistOnLastFMViewModel = ViewModelProvider(this).get(SearchArtistOnLastFMViewModel::class.java)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "More information of selected artist will be loaded", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            val keywordToSearchForArtistName = findViewById<TextView>(R.id.searchKeywordTextView).text.toString()
            if(keywordToSearchForArtistName.isEmpty()) {
                Snackbar.make(view, "Artist name cannot be blank", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                return@setOnClickListener
            } else if( keywordToSearchForArtistName.length < 2 ) {
                Snackbar.make(view, "Artist name must have at least 2 characters", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                return@setOnClickListener
            }
            searchArtistOnLastFMViewModel.fetchSearchResults(keywordToSearchForArtistName)

            val initialSearchResultObserver = Observer<MutableList<MutableMap<String, String>>> { it ->

                if(it[0].containsKey("ErrorMessage")) {
                    Snackbar.make(view, it[0].getValue("ErrorMessage"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    okToContinue = false // TODO This might not be necessary.
                    return@Observer
                }
                setupRecyclerView(findViewById(R.id.item_list), it)

            }

            searchArtistOnLastFMViewModel.artistsSearchResponseReceived.observe(this, initialSearchResultObserver)

        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

    }

    private fun setupRecyclerView(recyclerView: RecyclerView, dataToShow: MutableList<MutableMap<String, String>> ) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, dataToShow, twoPane)
    }

    // TODO move recyclerview out of this class.
    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                        private val values: MutableList<MutableMap<String, String>>,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as MutableMap<String, String>
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {//apply the following assignment to the object.
                            putString(ItemDetailFragment.ARG_ARTIST_NAME, item["name"])
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply { // apply the following assignments to the object.
                        putExtra(ItemDetailFragment.ARG_ARTIST_NAME, item["name"])
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.contentView1.text = "Artist name: " + item.getValue("name")
            holder.contentView2.text = "Number of listeners: " + item.getValue("listeners")

            // With this object, do the following.
            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val contentView1: TextView = view.findViewById(R.id.contentLine1)
            val contentView2: TextView = view.findViewById(R.id.contentLine2)
        }
    }
}
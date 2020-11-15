package com.example.lastfmsearchapp.repository

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/*API key: 2f6d1c3a77d7364fe9c8b7d955a56e11

Shared secret: 6f2327ff8b4d7ca6813f250b4aa35984

Registered to: NowOneFineUser*/

const val API_KEY = "2f6d1c3a77d7364fe9c8b7d955a56e11"

class LastFMSearchResultsRepository {

    //http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=YOUR_API_KEY&format=json
    suspend fun fetchSearchResults(keyWordsToSearchFor: String) {
        // Instantiate the RequestQueue.
/*        val queue = Volley.newRequestQueue()

        val url = "https://www.google.com"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    textView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener { textView.text = "That didn't work!" })



// Add the request to the RequestQueue.
        queue.add(stringRequest)


    }*/

    }
}
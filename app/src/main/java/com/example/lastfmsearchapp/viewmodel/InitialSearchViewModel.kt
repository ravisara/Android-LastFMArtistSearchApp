package com.example.lastfmsearchapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/*API key: 2f6d1c3a77d7364fe9c8b7d955a56e11

Shared secret: 6f2327ff8b4d7ca6813f250b4aa35984

Registered to: NowOneFineUser*/

const val API_KEY = "2f6d1c3a77d7364fe9c8b7d955a56e11"

class InitialSearchViewModel(application: Application) : AndroidViewModel(application) {

    // http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=YOUR_API_KEY&format=json

    fun fetchSearchResults(keyWordsToSearchFor: String = "believe") { // TODO remove this default text

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(getApplication()) // TODO review

        val url =
            "http://ws.audioscrobbler.com/2.0/?method=album.search&album=${keyWordsToSearchFor}&api_key=${API_KEY}&format=json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,
            null,
            { response ->
                println("Response: %s".format(response.toString()))
            },
            { error ->
                // TODO: Handle error
                println("That didn't work!")
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

        // Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        /*// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                // Display the first 500 characters of the response string.
                println("Response is: ${response.substring(0, 500)}")
            },
            { println("That didn't work!") })

            val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                println("Response is: ${response.substring(0, 500)}")
            },
            Response.ErrorListener { println("That didn't work!") })

            // Add the request to the RequestQueue.
            queue.add(stringRequest) */

    }


}
package com.example.lastfmsearchapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.lastfmsearchapp.utility.GlobalConstants.API_KEY
import com.example.lastfmsearchapp.utility.parseArtistsJSONObjectAndExtractDataForDisplaying

/*
* https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Cher&api_key=YOUR_API_KEY&format=json
* https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Cher&api_key=2f6d1c3a77d7364fe9c8b7d955a56e11&format=json
* */

class MoreInformationOfArtistViewModel(application: Application) : AndroidViewModel(application) {

    fun fetchArtistInformation(artistNameToSearchFor:String) {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(getApplication())

        val url = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=${artistNameToSearchFor}Cher&api_key=${API_KEY}&format=json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                println("Response: %s".format(response.toString()))
                val resultDataForDisplay = parseArtistsJSONObjectAndExtractDataForDisplaying(response)
            },
            { error ->
                // TODO: Handle error
                println("That didn't work!")
                //_responseReceived.value = "That didn't work!\r" + error.message
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }


}
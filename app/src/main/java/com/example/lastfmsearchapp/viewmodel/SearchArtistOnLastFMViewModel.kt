package com.example.lastfmsearchapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.lastfmsearchapp.utility.GlobalConstants.API_KEY
import com.example.lastfmsearchapp.utility.SingletonForVolleyLibrary
import com.example.lastfmsearchapp.utility.parseAnArtistInfoJSONObjectAndExtractDataForDisplaying
import com.example.lastfmsearchapp.utility.parseArtistsJSONObjectAndExtractDataForDisplaying

/*
API key: 2f6d1c3a77d7364fe9c8b7d955a56e11

Sample URL format: http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=YOUR_API_KEY&format=json
Artist search:
    https://ws.audioscrobbler.com/2.0/?method=artist.search&artist=cher&api_key=2f6d1c3a77d7364fe9c8b7d955a56e11&format=json
Album search:
    https://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=2f6d1c3a77d7364fe9c8b7d955a56e11&format=json
Song(track) search:
    https://ws.audioscrobbler.com/2.0/?method=track.search&track=Believe&api_key=2f6d1c3a77d7364fe9c8b7d955a56e11&format=json
*/

// TODO ideally, connecting to the API should have done in a separate Repository class and MVVM followed the repository pattern.
class SearchArtistOnLastFMViewModel(application: Application) : AndroidViewModel(application) {

    private val _artistsSearchResponseReceived =
        MutableLiveData<MutableList<MutableMap<String, String>>>()
    val artistsSearchResponseReceived: LiveData<MutableList<MutableMap<String, String>>> =
        _artistsSearchResponseReceived

    private val _artistInfoSearchResponseReceived = MutableLiveData<MutableMap<String, String>>()
    val artistInfoSearchResponseReceived: LiveData<MutableMap<String, String>> =
        _artistInfoSearchResponseReceived

    // TODO find a clever way of reporting errors.
    fun fetchSearchResults(keyWordsToSearchFor: String) { // TODO remove this default text

        // Instantiate the RequestQueue - using the Google recommended way.
        val queue = SingletonForVolleyLibrary.getInstance(getApplication()).requestQueue

        val url =
            "https://ws.audioscrobbler.com/2.0/?method=artist.search&artist=${keyWordsToSearchFor}&api_key=${API_KEY}&format=json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val resultDataForDisplay =
                    parseArtistsJSONObjectAndExtractDataForDisplaying(response)
                _artistsSearchResponseReceived.value =
                    resultDataForDisplay // instead of postvalue as we are calling from the main thread.
            },
            { error ->
                // TODO: Handle error
                println("That didn't work!")
                val allArtistsInfoToShow = mutableListOf<MutableMap<String, String>>()
                val errorMessageToShow = error?.message ?: "Unknown error"
                val errorMessage =  mutableMapOf("ErrorMessage" to errorMessageToShow)
                _artistsSearchResponseReceived.value =  mutableListOf(errorMessage)
            }
        )

       // Add a request (in this example, called stringRequest) to your RequestQueue.
        SingletonForVolleyLibrary.getInstance(getApplication()).addToRequestQueue(jsonObjectRequest)

    }

    /*
    * https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Cher&api_key=YOUR_API_KEY&format=json
    * https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Cher&api_key=2f6d1c3a77d7364fe9c8b7d955a56e11&format=json
    * */
    fun fetchArtistInformation(artistNameToSearchFor: String) {

        // Instantiate the RequestQueue - using the Google recommended way.
        val queue = SingletonForVolleyLibrary.getInstance(getApplication()).requestQueue

        val url =
            "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=${artistNameToSearchFor}&api_key=${API_KEY}&format=json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                println("Response: %s".format(response.toString()))
                val resultDataForDisplay =
                    parseAnArtistInfoJSONObjectAndExtractDataForDisplaying(response)
                _artistInfoSearchResponseReceived.value =
                    resultDataForDisplay  // instead of postvalue as we are calling from the main thread.
            },
            { error ->
                // TODO: Handle error
                println("That didn't work!")
                //_responseReceived.value = "That didn't work!\r" + error.message
            }
        )

        // Add the request to the RequestQueue.
        SingletonForVolleyLibrary.getInstance(getApplication()).addToRequestQueue(jsonObjectRequest)

    }

}
package com.example.lastfmsearchapp.repository

/*API key: 2f6d1c3a77d7364fe9c8b7d955a56e11

Shared secret: 6f2327ff8b4d7ca6813f250b4aa35984

Registered to: NowOneFineUser*/

const val API_KEY = "2f6d1c3a77d7364fe9c8b7d955a56e11"

class LastFMSearchResultsRepository {

    //http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=YOUR_API_KEY&format=json
    suspend fun fetchSearchResults(keyWordsToSearchFor: String) {
       val ok = OkHttpClient()

    }

}
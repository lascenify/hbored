package com.lascenify.hbored.util

import android.net.Uri
import com.lascenify.hbored.BuildConfig
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
const val SIZE = "original/" //possible options =  "w92", "w154", "w185", "w342", "w500", "w780", or "original"

const val BASE_QUERY_URL = "https://api.themoviedb.org/3/discover/movie"

const val apiKey = BuildConfig.TMDB_API_KEY

const val language = "en"
const val includeAdultContent = "true"

private const val API_KEY_PARAM = "api_key"
private const val LANGUAGE_PARAM = "language"
private const val SORT_BY_PARAM = "sort_by"
private const val INCLUDE_ADULT_PARAM = "include_adult"


fun buildUrl(sortBy:String): URL? {
    val uri = Uri.parse(BASE_QUERY_URL).buildUpon()
        .appendQueryParameter(API_KEY_PARAM, apiKey)
        .appendQueryParameter(LANGUAGE_PARAM, language)
        .appendQueryParameter(SORT_BY_PARAM, sortBy)
    return URL(uri.toString())
}

@Throws(IOException::class)
fun getResponseFromHttpUrl (url: URL):String?{
    val urlConnection =
        url.openConnection() as HttpURLConnection
    return try {
        val `in` = urlConnection.inputStream
        val scanner = Scanner(`in`)
        scanner.useDelimiter("\\A")
        val hasInput = scanner.hasNext()
        if (hasInput) {
            scanner.next()
        } else {
            null
        }
    } finally {
        urlConnection.disconnect()
    }
}
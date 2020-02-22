package com.lascenify.hbored.util

import com.lascenify.hbored.data.Movie
import com.lascenify.hbored.ui.MovieListFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection

const val OWN_RESULTS = "results"
const val OWN_TOTAL_RESULTS = "total_results"
const val OWN_POSTER_PATH = "poster_path"
const val OWN_MOVIE_ID = "id"
const val OWN_TITLE = "title"
const val OWN_RELEASE_DATE = "release_date"
const val OWN_OVERVIEW = "overview"
const val OWN_GENRE_IDS = "genre_ids"
const val OWM_MESSAGE_CODE = "status_code"
const val OWN_VOTE_COUNT = "vote_count"
const val OWN_VOTE_AVERAGE = "vote_average"

fun getMoviesFromJson(jsonString:String, sortedBy:String):ArrayList<Movie>?{
    val moviesJSON = JSONObject(jsonString)
    /* Is there an error? */if (moviesJSON.has(OWM_MESSAGE_CODE)) {
        when (moviesJSON.getInt(OWM_MESSAGE_CODE)) {
            HttpURLConnection.HTTP_OK -> {
            }
            HttpURLConnection.HTTP_NOT_FOUND ->  /* Location invalid */return null
            else ->  /* Server probably down */return null
        }
    }
    try {

        val moviesJsonArray = moviesJSON.getJSONArray(OWN_RESULTS)
        when (sortedBy){
            MovieListFragment.POPULARITY_QUERY -> return parseJsonMovieArraySortedByPopularity(moviesJsonArray)
            MovieListFragment.HIGHEST_RATED_QUERY -> return parseJsonMovieArraySortedByRate(moviesJsonArray)
            MovieListFragment.RECENT_QUERY -> return parseJsonMovieArraySortedByDate(moviesJsonArray)
            MovieListFragment.TITLE_ASC_QUERY, MovieListFragment.TITLE_DESC_QUERY -> return parseJsonMovieArraySortedByTitle(moviesJsonArray)
        }

    } catch (e:IOException){
        e.printStackTrace()
    }
    return null
}


private fun parseJsonMovieArraySortedByPopularity(
    moviesJsonArray: JSONArray) : ArrayList<Movie> {
    val moviesArrayList = arrayListOf<Movie>()
    for (i in 0 until moviesJsonArray.length()) {
        val movieObject = moviesJsonArray.getJSONObject(i)
        val imagePath = BASE_IMAGE_URL + SIZE + movieObject.getString(OWN_POSTER_PATH)
        val title = movieObject.getString(OWN_TITLE)
        val movieId = movieObject.getLong(OWN_MOVIE_ID)
        val releaseDate = movieObject.getString(OWN_RELEASE_DATE)
        val overview = movieObject.getString(OWN_OVERVIEW)

        val genreIdJsonArray = movieObject.getJSONArray(OWN_GENRE_IDS)
        val genreIdArray = mutableListOf<Int>()
        for (j in 0 until genreIdJsonArray.length()) {
            genreIdArray.add(genreIdJsonArray[j] as Int)
        }

        var voteCount = 0
        var voteAverage = 0.0
        if (movieObject.has(OWN_VOTE_COUNT)){
            voteCount = movieObject.getInt(OWN_VOTE_COUNT)
            voteAverage = movieObject.getDouble(OWN_VOTE_AVERAGE)
        }


        val movie = Movie(movieId, title, imagePath, releaseDate, overview, genreIdArray, voteCount, voteAverage)
        moviesArrayList.add(movie)
    }
    return moviesArrayList
}

private fun parseJsonMovieArraySortedByRate(
    moviesJsonArray: JSONArray) : ArrayList<Movie> {
    val moviesArrayList = arrayListOf<Movie>()
    for (i in 0 until moviesJsonArray.length()) {
        val movieObject = moviesJsonArray.getJSONObject(i)
        val imagePath = BASE_IMAGE_URL + SIZE + movieObject.getString(OWN_POSTER_PATH)
        val title = movieObject.getString(OWN_TITLE)
        val movieId = movieObject.getLong(OWN_MOVIE_ID)
        var releaseDate :String? = null
        if (movieObject.has(OWN_RELEASE_DATE)){
            releaseDate = movieObject.getString(OWN_RELEASE_DATE)
        }

        val overview = movieObject.getString(OWN_OVERVIEW)

        val genreIdJsonArray = movieObject.getJSONArray(OWN_GENRE_IDS)
        val genreIdArray = mutableListOf<Int>()
        for (j in 0 until genreIdJsonArray.length()) {
            genreIdArray.add(genreIdJsonArray[j] as Int)
        }

        var voteCount = 0
        var voteAverage = 0.0
        if (movieObject.has(OWN_VOTE_COUNT)){
            voteCount = movieObject.getInt(OWN_VOTE_COUNT)
            voteAverage = movieObject.getDouble(OWN_VOTE_AVERAGE)
        }


        val movie = Movie(movieId, title, imagePath, releaseDate, overview, genreIdArray, voteCount, voteAverage)
        moviesArrayList.add(movie)
    }
    return moviesArrayList
}


private fun parseJsonMovieArraySortedByTitle(
    moviesJsonArray: JSONArray) : ArrayList<Movie> {
    val moviesArrayList = arrayListOf<Movie>()
    for (i in 0 until moviesJsonArray.length()) {
        val movieObject = moviesJsonArray.getJSONObject(i)
        val imagePath = BASE_IMAGE_URL + SIZE + movieObject.getString(OWN_POSTER_PATH)
        val title = movieObject.getString(OWN_TITLE)
        val movieId = movieObject.getLong(OWN_MOVIE_ID)
        val overview = movieObject.getString(OWN_OVERVIEW)
        var releaseDate :String? = null
        if (movieObject.has(OWN_RELEASE_DATE)){
            releaseDate = movieObject.getString(OWN_RELEASE_DATE)
        }
        val genreIdJsonArray = movieObject.getJSONArray(OWN_GENRE_IDS)
        val genreIdArray = mutableListOf<Int>()
        for (j in 0 until genreIdJsonArray.length()) {
            genreIdArray.add(genreIdJsonArray[j] as Int)
        }

        var voteCount = 0
        var voteAverage = 0.0
        if (movieObject.has(OWN_VOTE_COUNT)){
            voteCount = movieObject.getInt(OWN_VOTE_COUNT)
            voteAverage = movieObject.getDouble(OWN_VOTE_AVERAGE)
        }


        val movie = Movie(movieId, title, imagePath, releaseDate, overview, genreIdArray, voteCount, voteAverage)
        moviesArrayList.add(movie)
    }
    return moviesArrayList
}

private fun parseJsonMovieArraySortedByDate(
    moviesJsonArray: JSONArray) : ArrayList<Movie> {
    val moviesArrayList = arrayListOf<Movie>()
    for (i in 0 until moviesJsonArray.length()) {
        val movieObject = moviesJsonArray.getJSONObject(i)
        val imagePath = BASE_IMAGE_URL + SIZE + movieObject.getString(OWN_POSTER_PATH)
        val title = movieObject.getString(OWN_TITLE)
        val movieId = movieObject.getLong(OWN_MOVIE_ID)
        var releaseDate :String? = null
        if (movieObject.has(OWN_RELEASE_DATE)){
            releaseDate = movieObject.getString(OWN_RELEASE_DATE)
        }
        val overview = movieObject.getString(OWN_OVERVIEW)

        val genreIdJsonArray = movieObject.getJSONArray(OWN_GENRE_IDS)
        val genreIdArray = mutableListOf<Int>()
        for (j in 0 until genreIdJsonArray.length()) {
            genreIdArray.add(genreIdJsonArray[j] as Int)
        }

        var voteCount = 0
        var voteAverage = 0.0
        if (movieObject.has(OWN_VOTE_COUNT)){
            voteCount = movieObject.getInt(OWN_VOTE_COUNT)
            voteAverage = movieObject.getDouble(OWN_VOTE_AVERAGE)
        }


        val movie = Movie(movieId, title, imagePath, releaseDate, overview, genreIdArray, voteCount, voteAverage)
        moviesArrayList.add(movie)
    }
    return moviesArrayList
}
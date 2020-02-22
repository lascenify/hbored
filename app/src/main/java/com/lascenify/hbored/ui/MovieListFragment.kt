package com.lascenify.hbored.ui

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.lascenify.hbored.R
import com.lascenify.hbored.data.Movie
import com.lascenify.hbored.databinding.FragmentMovieListBinding
import com.lascenify.hbored.util.buildUrl
import com.lascenify.hbored.util.getResponseFromHttpUrl
import com.lascenify.hbored.util.getMoviesFromJson
import java.io.IOException

class MovieListFragment : Fragment(), MovieAdapter.MovieAdapterOnClickHandler {
    private val SORT_BY_POPULARITY = "Popularity"
    private val SORT_BY_RECENT = "Recently added"
    private val SORT_BY_TITLE_ASC = "Title (Z-A)"
    private val SORT_BY_HIGHEST_RATED = "Highest rated"
    private val SORT_BY_TITLE_DESC = "Title (A-Z)"

    private val SPAN_COLUMNS = 2

    private val sortByHashMap = HashMap<String, String>()
    private var sortBy:String? = null

    companion object{
        lateinit var movieListRecyclerView : RecyclerView
        lateinit var mContext: Context
        lateinit var mAdapter:MovieAdapter

        private lateinit var binding:FragmentMovieListBinding

        const val POPULARITY_QUERY = "popularity.desc"
        const val RECENT_QUERY = "primary_release.desc"
        const val TITLE_ASC_QUERY = "original_title.asc"
        const val TITLE_DESC_QUERY = "original_title.desc"
        const val HIGHEST_RATED_QUERY = "vote_average.desc"

        fun showErrorMessage(){
            binding.errorMessageTv.visibility = VISIBLE
            binding.movieListRecyclerview.visibility = GONE
            binding.topLayout.visibility = GONE
        }

        fun showMovieList(){

            binding.errorMessageTv.visibility = GONE
            binding.movieListRecyclerview.visibility = VISIBLE
            binding.topLayout.visibility = VISIBLE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        val rootView = binding.root
        movieListRecyclerView = rootView.findViewById(R.id.movie_list_recyclerview)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mContext = context!!
        initOrderByHashMap()
        val layoutManager = GridLayoutManager(context, SPAN_COLUMNS, GridLayoutManager.VERTICAL, false)

        movieListRecyclerView.layoutManager = layoutManager
        mAdapter = MovieAdapter(this)
        movieListRecyclerView.adapter = mAdapter

        binding.movieListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.setSelection(0)
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemClicked = parent?.getItemAtPosition(position).toString()
                sortBy = sortByHashMap[itemClicked]!!
                loadMoviesData()
            }

        }

        loadMoviesData()
    }

    private fun initOrderByHashMap(){
        sortByHashMap[SORT_BY_POPULARITY] = POPULARITY_QUERY
        sortByHashMap[SORT_BY_HIGHEST_RATED] = HIGHEST_RATED_QUERY
        sortByHashMap[SORT_BY_RECENT] = RECENT_QUERY
        sortByHashMap[SORT_BY_TITLE_ASC] = TITLE_ASC_QUERY
        sortByHashMap[SORT_BY_TITLE_DESC] = TITLE_DESC_QUERY

        sortBy = sortByHashMap[SORT_BY_POPULARITY]!!
    }

    private fun loadMoviesData(){
        showMovieList()
        MovieFetchAsyncTask(sortBy!!).execute()
    }



    class MovieFetchAsyncTask(private val sortBy:String) : AsyncTask<String, Void, ArrayList<Movie>?>(){

        override fun onPreExecute() {
            binding.moviesPb.visibility = VISIBLE
        }

        override fun doInBackground(vararg params: String?): ArrayList<Movie>?{
            val url = buildUrl(sortBy) ?: return null
            try {
                val httpResponse = getResponseFromHttpUrl(url)
                val aux =  getMoviesFromJson(httpResponse!!, sortBy)
                return aux
            } catch (e:IOException){
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: ArrayList<Movie>?) {
            binding.moviesPb.visibility = GONE
            if (result.isNullOrEmpty()){
                showErrorMessage()
            }
            else {
                showMovieList()
                mAdapter.setMovieListData(result)
            }

        }
    }

    override fun onClick(clickedMovie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(getString(R.string.movie_bundle), clickedMovie)
        findNavController().navigate(R.id.movieDetailFragment, bundle)
    }


}

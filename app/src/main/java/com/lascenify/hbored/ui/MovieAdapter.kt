package com.lascenify.hbored.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lascenify.hbored.R
import com.lascenify.hbored.data.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(mHandler: MovieAdapterOnClickHandler) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface MovieAdapterOnClickHandler{
        fun onClick(clickedMovie :Movie)
    }

    fun setMovieListData(movies:ArrayList<Movie>){
        mMovieArrayList = movies
        notifyDataSetChanged()
    }

    companion object{
        private lateinit var mClickHandler : MovieAdapterOnClickHandler
        private var mMovieArrayList:ArrayList<Movie>? = null
        private lateinit var mContext:Context

    }

    init {
        mClickHandler = mHandler
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        mContext = parent.context
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = if (mMovieArrayList.isNullOrEmpty()) 0 else mMovieArrayList!!.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movieAtPosition = mMovieArrayList?.get(position)
        var imagePath = movieAtPosition!!.imageUrl
        if (imagePath.isEmpty()){
            Picasso.with(mContext)
                .load(R.drawable.ic_camera)
                .into(holder.imagePoster)
        }
        else {
            Picasso.with(mContext)
                .load(movieAtPosition!!.imageUrl)
                .into(holder.imagePoster)
        }
    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imagePoster:ImageView = itemView.findViewById(R.id.movie_item_iv)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val movie: Movie = mMovieArrayList?.get(adapterPosition)!!
            mClickHandler.onClick(movie)
        }
    }
}
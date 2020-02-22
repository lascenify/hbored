package com.lascenify.hbored.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.lascenify.hbored.R
import com.lascenify.hbored.data.Movie
import com.lascenify.hbored.databinding.FragmentMovieDetailBinding
import com.squareup.picasso.Picasso


class MovieDetailFragment :Fragment(){

    private lateinit var movie:Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentMovieDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)

        if (arguments != null){
            movie = arguments?.getParcelable(getString(R.string.movie_bundle))!!
            binding.movie = movie
        }


        Picasso.with(context)
            .load(movie.imageUrl)
            .into(binding.movieDetailImage)

        return binding.root
    }



}
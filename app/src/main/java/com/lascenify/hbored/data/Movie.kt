package com.lascenify.hbored.data

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.lascenify.hbored.R
import com.squareup.picasso.Picasso

class Movie(val movieId:Long,
            val title:String,
            val imageUrl:String,
            val releaseDate:String?,
            val overview:String,
            val genreIds:List<Int>,
            val voteCount:Int,
            val voteAverage:Double) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readArrayList(List::class.java.classLoader) as List<Int>,
        parcel.readInt(),
        parcel.readDouble()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(movieId)
        dest?.writeString(title)
        dest?.writeString(imageUrl)
        dest?.writeString(releaseDate)
        dest?.writeString(overview)
        dest?.writeList(genreIds)
        dest?.writeInt(voteCount)
        dest?.writeDouble(voteAverage)
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }


}

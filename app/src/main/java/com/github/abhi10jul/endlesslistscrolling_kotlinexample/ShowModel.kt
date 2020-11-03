package com.github.abhi10jul.endlesslistscrolling_kotlinexample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *  @author abhishek srivastava.
 */
data class ShowModel(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("pages")
    @Expose
    val pages: Int,
    @SerializedName("tv_shows")
    @Expose
    val tvShows: ArrayList<TvShow>
)

data class TvShow(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("image_thumbnail_path")
    @Expose val imageThumbnailPath: String
)
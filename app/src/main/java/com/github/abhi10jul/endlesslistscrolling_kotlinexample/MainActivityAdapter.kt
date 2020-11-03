package com.github.abhi10jul.endlesslistscrolling_kotlinexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.abhi10jul.endlesslistscrolling_kotlinexample.databinding.ShowListRawBinding

/**
 *  @author abhishek srivastava.
 */
class MainActivityAdapter() :
    RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterHolder>() {
    private var tvShowList = ArrayList<TvShow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterHolder {
        return MainActivityAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.show_list_raw, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainActivityAdapterHolder, position: Int) {
        holder.binding.tvTitle.text = "${tvShowList[position].name}"
        Glide
            .with(holder.itemView)
            .load(tvShowList[position].imageThumbnailPath)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.thumbnail)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    class MainActivityAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ShowListRawBinding.bind(itemView)
    }

    fun updateList(tvShowList: ArrayList<TvShow>, oldCount: Int, tvShowListSize: Int) {
        this.tvShowList = tvShowList
        notifyItemRangeInserted(oldCount, tvShowListSize)
    }
}
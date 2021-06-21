package com.sh.s1.made.mymovies.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sh.s1.made.mymovies.R
import com.sh.s1.made.mymovies.core.utils.GlideUtils.loadImage
import com.sh.s1.made.mymovies.databinding.ItemListMovieHorizontalBinding
import com.sh.s1.made.mymovies.domain.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie_horizontal, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListMovieHorizontalBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                ivItemImage.loadImage(data.posterPath)
                tvItemTitle.text = data.title
                tvItemSubtitle.text = data.overview
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }
    }
}
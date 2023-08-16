package com.example.first_project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.first_project.R
import com.example.first_project.databinding.ItemFavouritesBinding
import com.example.first_project.ui.database.ProductEntity

class FavouriteAdapter() : ListAdapter<ProductEntity, FavouriteAdapter.FavouriteHolder>(DiffCallBack()) {
    var onDeleteClick: (productEntity: ProductEntity) -> Unit = { _ -> }
    var onFullItemClick: (productEntity: ProductEntity) -> Unit = {_ ->}
    inner class FavouriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemFavouritesBinding.bind(itemView)
        fun bind(productEntity: ProductEntity) = with(binding) {
            brand.text = productEntity.brand
            description.text = productEntity.description
            Glide.with(itemView.context)
                .load(productEntity.picture)
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .into(picture)

            icDelete.setOnClickListener {
                onDeleteClick(productEntity)
            }

            itemView.setOnClickListener {
                onFullItemClick(productEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourites, parent, false)
        return FavouriteHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        val productEntity: ProductEntity = getItem(position)
        holder.bind(productEntity)
    }

    companion object {
        private class DiffCallBack : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem.brand == newItem.brand
            }

            override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
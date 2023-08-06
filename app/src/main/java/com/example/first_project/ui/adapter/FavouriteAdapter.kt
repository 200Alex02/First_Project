package com.example.first_project.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.first_project.R
import com.example.first_project.databinding.ItemFavouritesBinding
import com.example.first_project.ui.products.Product

class FavouriteAdapter() : ListAdapter<Product, FavouriteAdapter.FavouriteHolder>(DiffCallBack()) {
    var onDeleteClick: (product: Product) -> Unit = { _ -> }
    var onFullItemClick: (product: Product) -> Unit = {_ ->}
    inner class FavouriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemFavouritesBinding.bind(itemView)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(product: Product) = with(binding) {
            brand.text = product.brand
            description.text = product.description
            Glide.with(itemView.context)
                .load(product.picture)
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .into(picture)

            icDelete.setOnClickListener {
                onDeleteClick(product)
            }

            itemView.setOnClickListener {
                onFullItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourites, parent, false)
        return FavouriteHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        val product: Product = getItem(position)
        holder.bind(product)
    }

    companion object {
        private class DiffCallBack : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.brand == newItem.brand
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}
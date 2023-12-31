package com.example.first_project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.first_project.ui.products.Product
import com.example.first_project.R
import com.example.first_project.databinding.ItemProductsBinding

class ProductAdapter() : ListAdapter<Product, ProductAdapter.ProductHolder>(DiffCallBack()) {
    var onItemClick: (product: Product) -> Unit = { _ -> }
    var onItemLongClick: (product: Product) -> Unit = { _ -> }
    var onFullItemClick: (product: Product) -> Unit = { _ -> }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemProductsBinding.bind(itemView)
        fun bind(product: Product) = with(binding) {

            checkIsLike(product.likeElement)

            brand.text = product.brand
            description.text = product.description
            Glide.with(itemView.context)
                .load(product.picture)
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .into(picture)

            favouriteIcon.setOnClickListener {
                onItemClick(product)
                binding.favouriteIcon.setImageResource(R.drawable.ic_favourite_red_item)
            }

            favouriteIcon.setOnLongClickListener {
                onItemLongClick(product)
                true
            }

            itemView.setOnClickListener {
                onFullItemClick(product)
            }
        }

        private fun checkIsLike(isLike: Boolean) {
            if (isLike) {
                binding.favouriteIcon.setImageResource(R.drawable.ic_favourite_red_item)
            } else {
                binding.favouriteIcon.setImageResource(R.drawable.ic_favorite_item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
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
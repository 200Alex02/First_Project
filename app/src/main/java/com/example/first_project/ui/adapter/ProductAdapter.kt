package com.example.first_project.ui.adapter

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
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
import com.example.first_project.ui.room.ProductDb

class ProductAdapter(private val productsDb: List<ProductDb>) :
    ListAdapter<ProductDb, ProductAdapter.ProductHolder>(DiffCallBack()) {

    var onItemClick: (productDb: ProductDb) -> Unit = { _ -> }
    var onFullItemClick: (productDb: ProductDb) -> Unit = { _ -> }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemProductsBinding.bind(itemView)
        fun bind(productDb: ProductDb) = with(binding) {
            brand.text = productDb.brand
            description.text = productDb.description
            Glide.with(itemView.context)
                .load(productDb.picture)
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .into(picture)

            favouriteIcon.setOnClickListener {
                onItemClick(productDb)
                binding.favouriteIcon.setImageResource(R.drawable.ic_favourite_red)
            }

            itemView.setOnClickListener {
                onFullItemClick(productDb)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val productDb = productsDb[position]
        holder.bind(productDb)
    }

    companion object {
        private class DiffCallBack : DiffUtil.ItemCallback<ProductDb>() {
            override fun areItemsTheSame(oldItem: ProductDb, newItem: ProductDb): Boolean {
                return oldItem.brand == newItem.brand
            }

            override fun areContentsTheSame(oldItem: ProductDb, newItem: ProductDb): Boolean {
                return oldItem == newItem
            }
        }
    }

}
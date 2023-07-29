package com.example.first_project.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_project.R
import com.example.first_project.databinding.FragmentFavouriteBinding
import com.example.first_project.products
import com.example.first_project.ui.adapter.FavouriteAdapter
import com.example.first_project.ui.adapter.ProductAdapter
import com.example.first_project.ui.favourite.favouriteItemsList
import com.example.first_project.ui.products.Product

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: FavouriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPlaceholder()
        binding.recyclerViewFavourite.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavouriteAdapter()

        val onDeleteClick = { product: Product ->
            favouriteItemsList.remove(product)
            adapter.submitList(favouriteItemsList.toList())
            checkPlaceholder()
        }

        adapter.onDeleteClick = onDeleteClick
        binding.recyclerViewFavourite.adapter = adapter
        adapter.submitList(favouriteItemsList.toList())

        binding.addNewElements.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_item_favourite_to_item_home)
        }

    }

    private fun checkPlaceholder() {
        if (favouriteItemsList.isEmpty()) {
            binding.placeHolder.visibility = View.VISIBLE
            binding.layoutWithRc.visibility = View.INVISIBLE
        } else {
            binding.layoutWithRc.visibility = View.VISIBLE
            binding.placeHolder.visibility = View.INVISIBLE
        }
    }

}
package com.example.first_project.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.first_project.R
import com.example.first_project.databinding.FragmentFavouriteBinding
import com.example.first_project.ui.BaseFragment
import com.example.first_project.ui.adapter.FavouriteAdapter
import com.example.first_project.ui.favourite.favouriteItemsList
import com.example.first_project.ui.products.Product

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(
    FragmentFavouriteBinding::inflate
) {
    private lateinit var adapter: FavouriteAdapter
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
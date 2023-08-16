package com.example.first_project.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_project.R
import com.example.first_project.databinding.FragmentFavouriteBinding
import com.example.first_project.ui.basefragment.BaseFragment
import com.example.first_project.ui.adapter.FavouriteAdapter
import com.example.first_project.ui.database.ProductEntity
import com.example.first_project.ui.database.ProductMapper
import com.example.first_project.ui.dialogfragment.ClearFavoritesDialogFragment
import com.example.first_project.ui.products.Product
import com.example.first_project.ui.viewmodels.FavouriteViewModel

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(
    FragmentFavouriteBinding::inflate
) {
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var adapter: FavouriteAdapter
    private var isFabVisible = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        binding.recyclerViewFavourite.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavouriteAdapter()
        binding.recyclerViewFavourite.adapter = adapter

        val onDeleteClick = { productEntity: ProductEntity ->
            favouriteViewModel.deleteProduct(productEntity)
        }

        val onFullItemClick = { productEntity: ProductEntity ->
            val product: Product = ProductMapper.fromProductEntity(productEntity)
            val action =
                FavouriteFragmentDirections.actionItemFavouriteToDetailProductFragment2(product)
            findNavController().navigate(action)
        }

        adapter.onFullItemClick = onFullItemClick
        adapter.onDeleteClick = onDeleteClick

        favouriteViewModel.getAllProducts.observe(viewLifecycleOwner, Observer { productEntity ->
            adapter.submitList(productEntity)
            if (productEntity.isEmpty()) {
                binding.placeHolder.visibility = View.VISIBLE
                binding.layoutWithRc.visibility = View.INVISIBLE
            } else {
                binding.layoutWithRc.visibility = View.VISIBLE
                binding.placeHolder.visibility = View.INVISIBLE
            }
        })

        binding.addNewElements.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_item_favourite_to_item_home)
        }

        binding.removeFloatingBtn.setOnClickListener {
            showClearDialogFragment()
        }

        binding.recyclerViewFavourite.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && isFabVisible) {
                    binding.removeFloatingBtn.hide()
                    isFabVisible = false
                } else if (dy < 0 && !isFabVisible) {
                    binding.removeFloatingBtn.show()
                    isFabVisible = true
                }
            }
        })
    }

    private fun showClearDialogFragment() {
        val dialogFragment = ClearFavoritesDialogFragment()
        dialogFragment.show(parentFragmentManager, "clearFavouritesDialog")
    }
}